package com.giorgosgaganis.racing.atomicracetrack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {
    public static final int TRACK_LENGTH = 215;
    public static final int LANES = 3;

    private final AtomicIntegerArray[] raceTrack;
    private final AtomicBoolean upperLaneClaim;
    private final char name;

    private int position = 0;

    public Racer(char name, int startPosition, AtomicIntegerArray[] raceTrack, AtomicBoolean upperLaneClaim) {
        this.raceTrack = raceTrack;
        this.position = startPosition;
        this.name = name;
        this.upperLaneClaim = upperLaneClaim;
    }

    public static void main(String[] args) throws InterruptedException {
        runRace();
    }

    private static void runRace() throws InterruptedException {
        AtomicIntegerArray[] raceTrack = new AtomicIntegerArray[LANES];
        for (int i = 0; i < LANES; i++) {
            raceTrack[i] = new AtomicIntegerArray(TRACK_LENGTH);
        }
        beautifyTrack(raceTrack);
        AtomicBoolean upperLaneClaimed = new AtomicBoolean(false);

        Racer firstRacer = new Racer('f', 0, raceTrack, upperLaneClaimed);
        Racer secondRacer = new Racer('b', TRACK_LENGTH - 1, raceTrack, upperLaneClaimed);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(firstRacer);
        executorService.submit(secondRacer);
        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
        printTrack(raceTrack);
    }

    @Override
    public void run() {
        int direction = position == 0
                ? 1
                : -1;
        int lane = 1;

        for (; position <= TRACK_LENGTH - 1 && position >= 0; position += direction) {
            if (!raceTrack[lane].compareAndSet(position, '.', name)) {
                if (upperLaneClaim.compareAndSet(false, true)) {
                    lane = 0;
                    raceTrack[lane].set(position, name);
                } else {
                    lane = 2;
                    raceTrack[lane].set(position, name);
                }

            }
        }
    }

    private static void beautifyTrack(AtomicIntegerArray[] raceTrack) {
        for (AtomicIntegerArray lane : raceTrack) {
            for (int i = 0; i < lane.length(); i++) {
                lane.set(i, '.');
            }
        }
    }

    private static void printTrack(AtomicIntegerArray[] raceTrack) {
        for (AtomicIntegerArray lane : raceTrack) {
            for (int i = 0; i < lane.length(); i++) {
                System.out.print((char) lane.get(i));
            }
            System.out.println();
        }
    }
}


