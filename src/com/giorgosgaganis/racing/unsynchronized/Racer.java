package com.giorgosgaganis.racing.unsynchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {
    public static final int TRACK_LENGTH = 55215;
    public static final int LANES = 3;

    private final char[][] raceTrack;
    private static boolean upperLaneClaim = false;

    private final char name;

    private int position = 0;
    private int lane = 1;

    private Racer opponent;

    public Racer(char name, char[][] raceTrack, int startPosition) {
        this.raceTrack = raceTrack;
        this.position = startPosition;
        this.name = name;
    }

    public static void main(String[] args) throws InterruptedException {
        runRace();
    }

    private static void runRace() throws InterruptedException {
        char[][] raceTrack = new char[LANES][TRACK_LENGTH];
        beautifyTrack(raceTrack);

        Racer firstRacer = new Racer('f', raceTrack, 0);
        Racer secondRacer = new Racer('b', raceTrack, TRACK_LENGTH - 1);
        firstRacer.setOpponent(secondRacer);
        secondRacer.setOpponent(firstRacer);

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

        for (; position <= TRACK_LENGTH - 1 && position >= 0; position += direction) {
            boolean haveMet = direction > 0
                    ? opponent.position < position
                    : position < opponent.position;

            if (haveMet) {
                if (upperLaneClaim == false) {
                    upperLaneClaim = true;
                    lane--;
                    opponent.lane++;
                }
            }
            raceTrack[lane][position] = name;
        }
    }

    public void setOpponent(Racer opponent) {
        this.opponent = opponent;
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


