package com.giorgosgaganis.racing.atomicdistance;

import com.giorgosgaganis.racing.Race;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {

    private final char name;
    private final AtomicDistanceRace atomicDistanceRace;
    private int startPosition = 0;

    private final char[][] raceTrack;

    public Racer(char name, AtomicDistanceRace atomicDistanceRace, char[][] raceTrack, int startPosition) {
        this.name = name;
        this.atomicDistanceRace = atomicDistanceRace;
        this.raceTrack = raceTrack;
        this.startPosition = startPosition;
    }

    @Override
    public void run() {
        int direction = startPosition == 0
                ? 1
                : -1;

        boolean claimsComplete = false;
        int lane = 1;

        for (int position = startPosition;
             position <= Race.TRACK_LENGTH - 1 && position >= 0;
             position += direction) {

            AtomicInteger distance = atomicDistanceRace.distance;
            AtomicBoolean upperLaneClaim = atomicDistanceRace.upperLaneClaim;

            if (!claimsComplete && distance.getAndDecrement() <= 0) {
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
}
