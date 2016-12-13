package com.giorgosgaganis.racing.atomicdistance;

import com.giorgosgaganis.racing.Race;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDistanceRace implements Race {
    AtomicInteger distance;
    AtomicBoolean upperLaneClaim;

    public AtomicDistanceRace() {
    }

    @Override
    public void runRace(char[][] raceTrack) throws InterruptedException {

        distance = new AtomicInteger(Race.TRACK_LENGTH);
        upperLaneClaim = new AtomicBoolean(false);

        Racer firstRacer = new Racer('f', this,
                raceTrack, 0);
        Racer secondRacer = new Racer('b', this,
                raceTrack, Race.TRACK_LENGTH - 1);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(firstRacer);
        executorService.submit(secondRacer);
        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}