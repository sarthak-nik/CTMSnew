package com.anusarwant.ctms;

import java.util.Comparator;

public class Player {
    String name;
    int age;
    boolean isRight;
    int role; //-1: batsman   0: all rounder  1: spin bowler 2: fast bowler
    int tourBallsBowled,tourRunsScored,tourWickets,tourBallsPlayed, tourFours, tourSixes, tourOuts, tourRunsGiven;
    int matchBallsBowled, matchRunsScored, matchWicketsTaken, matchBallsPlayed, matchFours, matchSixes, matchRunsGiven;

    Player(String _name,int _age,boolean _isRight,int _role)
    {
        name=_name;
        age=_age;
        isRight=_isRight;
        role=_role;
        matchBallsPlayed=matchFours=matchSixes=matchBallsPlayed=matchWicketsTaken=matchBallsBowled=matchRunsGiven=matchRunsScored=0;
        tourBallsPlayed=tourRunsScored=tourFours=tourSixes=tourBallsBowled=tourWickets=tourRunsGiven=tourOuts=0;
    }

    public static Comparator<Player> playerComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p1.role-p2.role;
        }
    };
}
