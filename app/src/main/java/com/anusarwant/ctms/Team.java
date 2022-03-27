package com.anusarwant.ctms;
import java.util.*;

import javax.xml.validation.Validator;

public class Team {
    String name;
    ArrayList<Player> playersList;
    String captain;
    int wins,losses;
    Vector<Integer> matchHistory = new Vector<Integer>();

    Team(String _name)
    {
        name=_name;
        wins=losses=0;
    }


}
