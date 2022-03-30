package com.anusarwant.ctms;

import java.util.Comparator;

public class Match {
    int matchNum;
    Team team1,team2;
    String winner, team1score, team2score, battedFirst, toss;
    boolean isDone;
    String pom,highestRunScorer,highestWicketTaker,tournamentName;
    // constructor for creating the match object with team as arguments
    Match(Team _team1,Team _team2)
    {
        team1=_team1;
        team2=_team2;
        team1score="Yet to Bat";
        team2score="Yet to Bat";
        isDone = false;
        winner="TBD";
        battedFirst="TBD";
        toss="TBD";
    }
    //comparator function for match
    //used while sorting match list according to the match number in ascending order
    public static Comparator<Match> matchComparator = new Comparator<Match>() {
        @Override
        public int compare(Match m1, Match m2) {
            return m1.matchNum-m2.matchNum;
        }
    };
}
