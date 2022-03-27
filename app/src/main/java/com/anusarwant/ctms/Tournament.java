package com.anusarwant.ctms;

import java.util.ArrayList;

public class Tournament {
    String name;
    int nTeams;
    int nOvers;
    ArrayList<Team> teamsArray;
    ArrayList<Match> matchesArray;
    String status;

    Tournament(int novers, int nteams, String tourName){
        nTeams = nteams;
        nOvers = novers;
        name = tourName;
        status = "Ongoing";
        teamsArray = new ArrayList<>();
        matchesArray = new ArrayList<>();
    }

}
