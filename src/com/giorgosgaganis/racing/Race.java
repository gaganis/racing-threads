package com.giorgosgaganis.racing;

/**
 * Created by gaganis on 13/12/16.
 */
public interface Race {
    int TRACK_LENGTH = 215;
    int LANES = 3;

    void runRace(char[][] raceTrack) throws InterruptedException;
}
