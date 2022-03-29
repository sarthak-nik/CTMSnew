package com.anusarwant.ctms;

import java.util.Comparator;

public class Match {
    int matchNum;
    Team team1,team2;
    String winner, team1score, team2score, battedFirst;
    boolean isDone;
    String pom,highestRunScorer,highestWicketTaker,tournamentName;

    Match(Team _team1,Team _team2)
    {
        team1=_team1;
        team2=_team2;
        team1score="Yet to Bat";
        team2score="Yet to Bat";
        isDone = false;
    }

    public static Comparator<Match> matchComparator = new Comparator<Match>() {
        @Override
        public int compare(Match m1, Match m2) {
            return m1.matchNum-m2.matchNum;
        }
    };
}
