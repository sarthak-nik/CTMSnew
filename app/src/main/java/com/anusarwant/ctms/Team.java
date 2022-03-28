package com.anusarwant.ctms;
import java.util.*;

import javax.xml.validation.Validator;

public class Team {
    String name;
    ArrayList<Player> playersList;
    String captain;
    int wins,losses,draws;
    Vector<Integer> matchHistory;

    Team(String _name)
    {
        name=_name;
        wins=losses=draws=0;
        playersList = new ArrayList<>();
        matchHistory = new Vector<>();
    }
}
