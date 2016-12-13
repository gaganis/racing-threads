package com.giorgosgaganis.racing.locking;

import com.giorgosgaganis.racing.Race;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {

    private final char name;
    private final LockingRace lockingRace;
    private final char[][] raceTrack;

    private int position = 0;
    private int lane = 1;

    private Racer opponent;

    public Racer(char name, LockingRace lockingRace, char[][] raceTrack, int startPosition) {
        this.name = name;
        this.lockingRace = lockingRace;
        this.raceTrack = raceTrack;
        this.position = startPosition;
    }

    @Override
    public void run() {
        int direction = position == 0
                ? 1
                : -1;

        for (; ; ) {
            lockingRace.lock.lock();
            try {
                if (position < 0 || position > Race.TRACK_LENGTH - 1) {
                    break;
                }
                boolean haveMet = direction > 0
                        ? opponent.position < position
                        : position < opponent.position;

                if (haveMet) {
                    if (lockingRace.upperLaneClaim == false) {
                        lockingRace.upperLaneClaim = true;
                        lane--;
                        opponent.lane++;
                    }
                }
                raceTrack[lane][position] = name;
                position += direction;
            } finally {
                lockingRace.lock.unlock();
            }
        }
    }

    public void setOpponent(Racer opponent) {
        this.opponent = opponent;
    }
}
