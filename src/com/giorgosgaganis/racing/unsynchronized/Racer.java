package com.giorgosgaganis.racing.unsynchronized;

import com.giorgosgaganis.racing.Race;

/**
 * Created by gaganis on 09/12/16.
 */
public class Racer implements Runnable {
    private final UnsynchronizedRace race;
    private final char[][] raceTrack;

    private final char name;

    private int position = 0;
    private int lane = 1;

    private Racer opponent;

    public Racer(char name, UnsynchronizedRace race, char[][] raceTrack, int startPosition) {
        this.raceTrack = raceTrack;
        this.position = startPosition;
        this.name = name;
        this.race = race;
    }

    @Override
    public void run() {
        int direction = position == 0
                ? 1
                : -1;

        for (; position <= Race.TRACK_LENGTH - 1 && position >= 0; position += direction) {
            boolean haveMet = direction > 0
                    ? opponent.position < position
                    : position < opponent.position;

            if (haveMet) {
                if (race.upperLaneClaim == false) {
                    race.upperLaneClaim = true;
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

}


