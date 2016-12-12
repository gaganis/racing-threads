package com.giorgosgaganis.racing;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by gaganis on 12/12/16.
 */
public class RaceVerifierTest {

    @Test
    public void more_than_1_changes_on_middle_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "..f".toCharArray();
        raceTrack[1] = "fbf".toCharArray();
        raceTrack[2] = "b..".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void clear_forwardwinner_is_true() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bbb".toCharArray();
        raceTrack[1] = "fff".toCharArray();
        raceTrack[2] = "...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isTrue();
    }

    @Test
    public void clear_backwinner_is_true() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "fff".toCharArray();
        raceTrack[1] = "bbb".toCharArray();
        raceTrack[2] = "...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isTrue();
    }

    @Test
    public void clear_forwardwinner_missing_back_marks_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bb.".toCharArray();
        raceTrack[1] = "fff".toCharArray();
        raceTrack[2] = "...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void clear_backwinner_missing_forward_marks_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = ".ff".toCharArray();
        raceTrack[1] = "bbb".toCharArray();
        raceTrack[2] = "...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void midlane_not_fully_marked_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "fff".toCharArray();
        raceTrack[1] = "b.b".toCharArray();
        raceTrack[2] = "...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void forward_wins_upper_good_is_true() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "..ff".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "bb..".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isTrue();
    }

    @Test
    public void back_wins_upper_good_is_true() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bb..".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "..ff".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isTrue();
    }

    @Test
    public void forward_overshoots_up_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "...f".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "bb..".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void back_overshoots_down_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "..ff".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "b...".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void back_overshoots_up_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "b...".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "..ff".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void forward_overshoots_down_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bb..".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "...f".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void only_one_change_lane_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bb..".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "....".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void no_lane_change_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "....".toCharArray();
        raceTrack[1] = "ffbb".toCharArray();
        raceTrack[2] = "....".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void no_lane_change_and_overwite_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "....".toCharArray();
        raceTrack[1] = "bbff".toCharArray();
        raceTrack[2] = "....".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void forward_win_extra_garbage_up_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "b.fffff".toCharArray();
        raceTrack[1] = "ffbbbbb".toCharArray();
        raceTrack[2] = "bb.....".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void forward_win_extra_garbage_down_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "..fffff".toCharArray();
        raceTrack[1] = "ffbbbbb".toCharArray();
        raceTrack[2] = "bb....f".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void back_win_extra_garbage_up_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bbb...b".toCharArray();
        raceTrack[1] = "fffbbbb".toCharArray();
        raceTrack[2] = "...ffff".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }

    @Test
    public void back_win_extra_garbage_down_is_false() {
        char[][] raceTrack = new char[3][3];
        raceTrack[0] = "bbb....".toCharArray();
        raceTrack[1] = "fffbbbb".toCharArray();
        raceTrack[2] = "f..ffff".toCharArray();

        assertThat(RaceVerifier.verifyResult(raceTrack)).isFalse();
    }
}