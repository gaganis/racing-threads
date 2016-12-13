package com.giorgosgaganis.racing.unsynchronized;

import com.giorgosgaganis.racing.Race;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UnsynchronizedRace implements Race {
    boolean upperLaneClaim = false;

    @Override
    public void runRace(char[][] raceTrack) throws InterruptedException {

        Racer firstRacer = new Racer('f', this, raceTrack, 0);
        Racer secondRacer = new Racer('b', this, raceTrack, Race.TRACK_LENGTH - 1);
        firstRacer.setOpponent(secondRacer);
        secondRacer.setOpponent(firstRacer);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(firstRacer);
        executorService.submit(secondRacer);
        executorService.shutdown();

        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}