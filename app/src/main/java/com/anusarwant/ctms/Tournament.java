package com.anusarwant.ctms;

import java.util.ArrayList;

public class Tournament {
    String name;
    int nTeams;
    int nOvers;
    ArrayList<Team> teams;
    ArrayList<Match> matches;
    String status;

    Tournament(int novers, int nteams, String tourName){
        nTeams = nteams;
        nOvers = novers;
        name = tourName;
        status = "Ongoing";
    }

}
