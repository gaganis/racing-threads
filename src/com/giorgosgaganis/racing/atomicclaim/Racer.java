package com.giorgosgaganis.racing.atomicclaim;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static com.giorgosgaganis.racing.Race.TRACK_LENGTH;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {

    private final char name;
    private final AtomicClaimRace atomicClaimRace;
    private int startPosition = 0;

    private final char[][] raceTrack;

    public Racer(char name, AtomicClaimRace atomicClaimRace, char[][] raceTrack, int startPosition) {
        this.name = name;
        this.atomicClaimRace = atomicClaimRace;
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
             position <= TRACK_LENGTH - 1 && position >= 0;
             position += direction) {

            AtomicIntegerArray centerLaneClaims = atomicClaimRace.centerLaneClaims;
            AtomicBoolean upperLaneClaim = atomicClaimRace.upperLaneClaim;

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
}
