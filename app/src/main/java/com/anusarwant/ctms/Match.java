package com.anusarwant.ctms;

import java.util.Comparator;

public class Match {
    int matchNum;
    Team team1,team2;
    String winner;
    String pom,highestRunScorer,highestWicketTaker;

    Match(Team _team1,Team _team2)
    {
        team1=_team1;
        team2=_team2;
    }

    public static Comparator<Match> matchComparator = new Comparator<Match>() {
        @Override
        public int compare(Match m1, Match m2) {
            return m1.matchNum-m2.matchNum;
        }
    };
}
