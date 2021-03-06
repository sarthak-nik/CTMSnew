package com.anusarwant.ctms;

import java.util.Comparator;

public class Player {
    String name;
    int age;
    boolean isRight;
    int role; //-1: batsman   0: all rounder  1: spin bowler 2: fast bowler
    int tourBallsBowled,tourRunsScored,tourWickets,tourBallsPlayed, tourFours, tourSixes, tourOuts, tourRunsGiven;
    int matchBallsBowled, matchRunsScored, matchWicketsTaken, matchBallsPlayed, matchFours, matchSixes, matchRunsGiven;
    double tourStrikeRate,tourEconomy;

    // Constructor
    Player(String _name,int _age,boolean _isRight,int _role)
    {
        name=_name;
        age=_age;
        isRight=_isRight;
        role=_role;
        matchBallsPlayed=matchFours=matchSixes=matchBallsPlayed=matchWicketsTaken=matchBallsBowled=matchRunsGiven=matchRunsScored=0;
        tourBallsPlayed=tourRunsScored=tourFours=tourSixes=tourBallsBowled=tourWickets=tourRunsGiven=tourOuts=0;
        tourStrikeRate=0.0;
        tourEconomy=0.0;
    }

    // Comparator function that sorts according to role
    public static Comparator<Player> playerComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p1.role-p2.role;
        }
    };

    // Comparator function that sorts according to Runs Scored
    public static Comparator<Player> playersRunsComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p2.tourRunsScored-p1.tourRunsScored;
        }
    };

    // Comparator function that sorts according to Wickets Taken
    public static Comparator<Player> playerWicketsComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p2.tourWickets-p1.tourWickets;
        }
    };

    // Comparator function that sorts according to Strike Rate
    public static Comparator<Player> playerStrikeRateComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            double temp = (p2.tourStrikeRate-p1.tourStrikeRate);
            if (temp<0){
                return -1;
            }
            return  1;
        }
    };

    // Comparator function that sorts according to Bowling Economy
    public static Comparator<Player> playerEconomyComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            double temp = (p1.tourEconomy-p2.tourEconomy);
            if (temp<0){
                return -1;
            }
            return  1;
        }
    };

    // Comparator function that sorts according to number of fours hit
    public static Comparator<Player> playerFoursComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p2.tourFours-p1.tourFours;
        }
    };

    // Comparator function that sorts according to number of sixes hit
    public static Comparator<Player> playerSixesComparator = new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
            return p2.tourSixes-p1.tourSixes;
        }
    };

}
