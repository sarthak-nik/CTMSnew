package com.anusarwant.ctms;
import java.util.*;

import javax.xml.validation.Validator;

public class Team {
    String name;
    ArrayList<Player> playersList;
    String captain;
    int wins,losses,draws;
    int points;
    int tourPosition;
    Vector<Integer> matchHistory;       // 1:won     0:draw     -1:lost

    // Constructor
    Team(String _name)
    {
        name=_name;
        wins=0;
        losses=0;
        draws=0;
        points=0;
        playersList = new ArrayList<>();
        matchHistory = new Vector<>();
    }

    // Comparator Function
    public static Comparator<Team> teamComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t2.points- t1.points;
        }
    };
}
