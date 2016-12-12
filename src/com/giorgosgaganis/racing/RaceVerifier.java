package com.giorgosgaganis.racing;

/**
 * Created by gaganis on 12/12/16.
 */
public class RaceVerifier {

    public static final int MID_LANE = 1;
    public static final int UP_LANE = 0;
    public static final int DOWN_LANE = 2;

    public static boolean verifyResult(char[][] raceTrack) {
        int raceTrackLength = raceTrack[UP_LANE].length;
        char prevChar = raceTrack[MID_LANE][0];

        int changePoint = -1;
        for (int i = 0; i < raceTrackLength; i++) {
            char c = raceTrack[MID_LANE][i];
            if (c != 'f' && c != 'b') { //Middle lane must be completely marked
                return false;
            }
            if (c != prevChar) {
                if (changePoint != -1) { //More than one changes in midline
                    return false;
                } else {
                    changePoint = i;
                    prevChar = c;
                }
            }
        }
        if (changePoint == -1) { //Clear winner we had no changes on the middle lane
            for (int i = 0; i < raceTrackLength; i++) {
                if (raceTrack[UP_LANE][i] != opponent(prevChar)
                        || raceTrack[DOWN_LANE][i] != '.') {
                    return false;
                }
            }
            return true;
        }

        boolean overShot = true;
        if (raceTrack[UP_LANE][changePoint] == 'f') { //Forward won the upper lane
            overShot = false;
            if (areUpAndDownLanesBroken('f', raceTrack, raceTrackLength, changePoint)) return false;
        }

        if (raceTrack[DOWN_LANE][changePoint] == 'f') { //Back won the upper lane
            overShot = false;
            if (areUpAndDownLanesBroken('b', raceTrack, raceTrackLength, changePoint)) return false;
        }

        return !overShot;
    }

    private static boolean areUpAndDownLanesBroken(char winner, char[][] raceTrack, int raceTrackLength, int changePoint) {
        int forwardLane;
        int backLane;
        if (winner == 'f') {
            forwardLane = UP_LANE;
            backLane = DOWN_LANE;
        } else {
            forwardLane = DOWN_LANE;
            backLane = UP_LANE;
        }

        for (int i = 0; i < raceTrackLength; i++) {
            if (i < changePoint && raceTrack[forwardLane][i] != '.') {
                return true;
            }
            if (i >= changePoint && raceTrack[forwardLane][i] != 'f') {
                return true;
            }
        }
        for (int i = raceTrackLength - 1; i > 0; i--) {
            if (i < changePoint && raceTrack[backLane][i] != 'b') {
                return true;
            }
            if (i >= changePoint && raceTrack[backLane][i] != '.') {
                return true;
            }
        }
        return false;
    }

    private static char opponent(char c) {
        if (c == 'b') return 'f';

        if (c == 'f') return 'b';

        return c;
    }
}
