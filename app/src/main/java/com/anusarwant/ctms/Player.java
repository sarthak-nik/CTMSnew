package com.anusarwant.ctms;

public class Player {
    String name;
    int age;
    boolean isRight;
    int role; //-1: batsman   0: bowler  1:all rounder
    int oversBowled,runScored,wickets,economy,strikeRate;

    Player(String _name,int _age,boolean _isRight,int _role)
    {
        name=_name;
        age=_age;
        isRight=_isRight;
        role=_role;
        age=0;
        oversBowled=runScored=wickets=economy=strikeRate=0;
    }

}
