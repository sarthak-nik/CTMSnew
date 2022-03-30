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

    Team(String _name)
    {
        name=_name;
        wins=losses=draws=points=0;
        playersList = new ArrayList<>();
        matchHistory = new Vector<>();
    }

    public static Comparator<Team> teamComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t2.points- t1.points;
        }
    };
}
