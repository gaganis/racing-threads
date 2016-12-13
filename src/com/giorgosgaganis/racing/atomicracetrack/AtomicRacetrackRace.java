package com.giorgosgaganis.racing.atomicracetrack;

import com.giorgosgaganis.racing.Race;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicRacetrackRace implements Race {
    AtomicIntegerArray[] atomicRaceTrack;
    AtomicBoolean upperLaneClaimed;

    @Override
    public void runRace(char[][] raceTrack) throws InterruptedException {
        atomicRaceTrack = new AtomicIntegerArray[Race.LANES];
        for (int i = 0; i < Race.LANES; i++) {
            atomicRaceTrack[i] = new AtomicIntegerArray(Race.TRACK_LENGTH);
        }

        for (int i = 0; i < raceTrack.length; i++) {
            for (int j = 0; j < raceTrack[i].length; j++) {
                atomicRaceTrack[i].set(j, raceTrack[i][j]);
            }
        }

        upperLaneClaimed = new AtomicBoolean(false);

        Racer firstRacer = new Racer('f', this, 0);
        Racer secondRacer = new Racer('b', this, Race.TRACK_LENGTH - 1);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(firstRacer);
        executorService.submit(secondRacer);
        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
        for (int i = 0; i < raceTrack.length; i++) {
            for (int j = 0; j < raceTrack[i].length; j++) {
                raceTrack[i][j] = (char) atomicRaceTrack[i].get(j);
            }
        }
    }

}