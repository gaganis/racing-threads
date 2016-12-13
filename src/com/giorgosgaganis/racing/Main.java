package com.giorgosgaganis.racing;

import com.giorgosgaganis.racing.atomicclaim.AtomicClaimRace;
import com.giorgosgaganis.racing.atomicdistance.AtomicDistanceRace;
import com.giorgosgaganis.racing.atomicracetrack.AtomicRacetrackRace;
import com.giorgosgaganis.racing.locking.LockingRace;
import com.giorgosgaganis.racing.unsynchronized.UnsynchronizedRace;

/**
 * Created by gaganis on 13/12/16.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        char[][] raceTrack = new char[Race.LANES][Race.TRACK_LENGTH];
        beautifyTrack(raceTrack);

        String algorithm = "unsynchronized";

        if (args.length > 0) {
            algorithm = args[0];
        }

        System.out.printf("Running experiment with %s race\n\n", algorithm);

        Race race;
        switch (algorithm) {
            case "atomic-distance":
                race = new AtomicDistanceRace();
                break;
            case "atomic-claim":
                race = new AtomicClaimRace();
                break;
            case "atomic-racetrack":
                race = new AtomicRacetrackRace();
                break;
            case "locking":
                race = new LockingRace();
                break;
            case "unsynchronized":
                race = new UnsynchronizedRace();
                break;
            default:
                race = null;
                System.out.printf("Sorry, cannot recognise algorithm %s. \nAborting \n", algorithm);
                System.exit(1);
        }

        race.runRace(raceTrack);

        printTrack(raceTrack);

        if (RaceVerifier.verifyResult(raceTrack)) {
            System.out.println("Result OK");
        } else {
            System.out.println("Racers have crashed!(ie the result is not legal)");
            System.exit(1);
        }
    }

    static void beautifyTrack(char[][] raceTrack) {
        for (char[] lane : raceTrack) {
            for (int i = 0; i < lane.length; i++) {
                lane[i] = '.';
            }
        }
    }

    static void printTrack(char[][] raceTrack) {
        for (char[] lane : raceTrack) {
            for (char c : lane) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
