package com.giorgosgaganis.racing.atomicclaim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {
    public static final int TRACK_LENGTH = 165;
    public static final int LANES = 3;


    private final char name;
    private int startPosition = 0;

    private final char[][] raceTrack;

    private final AtomicIntegerArray centerLaneClaims;
    private final AtomicBoolean upperLaneClaim;

    public Racer(char name, int startPosition, char[][] raceTrack,
                 AtomicIntegerArray centerLaneClaims, AtomicBoolean upperLaneClaim) {
        this.raceTrack = raceTrack;
        this.startPosition = startPosition;
        this.name = name;
        this.centerLaneClaims = centerLaneClaims;
        this.upperLaneClaim = upperLaneClaim;
    }

    public static void main(String[] args) throws InterruptedException {
        runRace();
    }

    private static void runRace() throws InterruptedException {
        char[][] raceTrack = new char[LANES][TRACK_LENGTH];
        beautifyTrack(raceTrack);

        AtomicIntegerArray centerLaneClaims = new AtomicIntegerArray(TRACK_LENGTH);
        AtomicBoolean upperLaneClaim = new AtomicBoolean(false);

        Racer firstRacer = new Racer('f', 0,
                raceTrack, centerLaneClaims, upperLaneClaim);
        Racer secondRacer = new Racer('b', TRACK_LENGTH - 1,
                raceTrack, centerLaneClaims, upperLaneClaim);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(firstRacer);
        executorService.submit(secondRacer);
        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
        printTrack(raceTrack);
    }

    @Override
    public void run() {
        int direction = startPosition == 0
                ? 1
                : -1;

        boolean claimsComplete = false;
        int lane = 1;

        for (int position = startPosition;
             position <= TRACK_LENGTH - 1 && position >= 0;
             position += direction) {
            if (!claimsComplete && !centerLaneClaims.compareAndSet(position, 0, 1)) {
                if (upperLaneClaim.compareAndSet(false, true)) {
                    lane = 0;
                } else {
                    lane = 2;
                }
                claimsComplete = true;
            }
            raceTrack[lane][position] = name;
        }
    }

    private static void beautifyTrack(char[][] raceTrack) {
        for (char[] lane : raceTrack) {
            for (int i = 0; i < lane.length; i++) {
                lane[i] = '.';
            }
        }
    }

    private static void printTrack(char[][] raceTrack) {
        for (char[] lane : raceTrack) {
            for (char c : lane) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}


