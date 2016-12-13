package com.giorgosgaganis.racing.atomicracetrack;

import com.giorgosgaganis.racing.Race;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {
    private final char name;
    private final AtomicRacetrackRace atomicRacetrackRace;

    private int position = 0;

    public Racer(char name, AtomicRacetrackRace atomicRacetrackRace, int startPosition) {
        this.name = name;
        this.atomicRacetrackRace = atomicRacetrackRace;
        this.position = startPosition;
    }

    @Override
    public void run() {
        int direction = position == 0
                ? 1
                : -1;
        int lane = 1;

        for (; position <= Race.TRACK_LENGTH - 1 && position >= 0; position += direction) {

            AtomicIntegerArray[] atomicRaceTrack = atomicRacetrackRace.atomicRaceTrack;
            AtomicBoolean upperLaneClaimed = atomicRacetrackRace.upperLaneClaimed;

            if (!atomicRaceTrack[lane].compareAndSet(position, '.', name)) {
                if (upperLaneClaimed.compareAndSet(false, true)) {
                    lane = 0;
                    atomicRaceTrack[lane].set(position, name);
                } else {
                    lane = 2;
                    atomicRaceTrack[lane].set(position, name);
                }
            }
        }
    }
}


