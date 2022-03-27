package com.anusarwant.ctms;

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
}
