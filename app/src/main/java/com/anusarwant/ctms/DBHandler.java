package com.anusarwant.ctms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    //creating constant variables for databases
    private static final String DB_NAME = "ctms.db";
    private static final int DB_VERSION = 1;
    //creating variable for table name
    private static final String TABLE_NAME = "stats";
    //creating variable for tournament name column
    private static final String TOURNAMENT_COL = "tournament";
    //creating variable for match number column
    private static final String MATCH_COL = "matches";
    //creating variable for innings number column
    private static final String INNINGS_COL = "innings";
    //creating variable for player name column
    private static final String PLAYER_COL = "player";
    //creating variable for runs column
    private static final String RUNS_COL = "runs_scored";
    //creating variable for balls played column
    private static final String BALLS_COL = "ballsPlayed";
    //creating variable for fours name column
    private static final String FOUR_COL = "fours";
    //creating variable for six name column
    private static final String SIX_COL = "sixes";
    //creating variable for strike rate column
    private static final String STRIKE_COL = "strikeRate";
    //creating variable for batting average column
    private static final String BATAVG_COL = "batAvg";
    //creating variable for wickets column
    private static final String WICKETS_COL = "wicketsTaken";
    //creating variable for balls bowled column
    private static final String BALLSBOWLED_COL = "ballsBowled";
    //creating variable for runs given column
    private static final String RUNSGIVEN_COL = "runsGiven";
    //creating variable for economy column
    private static final String ECONOMY_COL = "economy";
    //creating variable for bowling average column
    private static final String BOWLAVG_COL = "bowlAvg";


    // creating a constructor for our database handler
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + TOURNAMENT_COL + " TEXT, "
                + MATCH_COL + " TEXT,"
                + PLAYER_COL + " TEXT,"
                + RUNS_COL + " INTEGER,"
                + BALLS_COL + " INTEGER,"
                + FOUR_COL + " INTEGER,"
                + SIX_COL + " INTEGER,"
                + STRIKE_COL + " DOUB,"
                + WICKETS_COL + " INTEGER,"
                + BALLSBOWLED_COL + " INTEGER,"
                + RUNSGIVEN_COL + " INTEGER,"
                + ECONOMY_COL + " DOUB)";


        db.execSQL(query);
    }
    public void addNewRow(String tournament,int match,String player,int runs,int ballsPlayed,int fours,int sixes,
                          double strikeRate,int ballsBowled,int wickets,int runsGiven,double economy)
    {

        //getting writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        // variable for content values.
        ContentValues values = new ContentValues();

        //storing all values
        values.put(TOURNAMENT_COL,tournament);
        values.put(MATCH_COL,match);
        values.put(PLAYER_COL,player);
        values.put(RUNS_COL,runs);
        values.put(BALLS_COL,ballsPlayed);
        values.put(FOUR_COL,fours);
        values.put(SIX_COL,sixes);
        values.put(STRIKE_COL,strikeRate);
        values.put(WICKETS_COL,wickets);
        values.put(BALLSBOWLED_COL,ballsBowled);
        values.put(RUNSGIVEN_COL,runsGiven);
        values.put(ECONOMY_COL,economy);

        //adding content values to the table
        db.insert(TABLE_NAME, null, values);
        Log.d("test",tournament);
        // at last we are closing our database
        db.close();
    }

    public Cursor getDetails(String tournament,int matchNum){
        //getting our readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //creating a cursor
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+TOURNAMENT_COL+"=? AND "+MATCH_COL+"=?",new String[]{tournament,Integer.toString(matchNum)});

        cursor.moveToFirst();

        return  cursor;

    }
    public Cursor getPlayerDetails(String tournament,String player){
        //getting our readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //creating a cursor
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+TOURNAMENT_COL+"=? AND "+PLAYER_COL+"=?",new String[]{tournament,player});

        cursor.moveToFirst();

        return  cursor;
    }
    public Cursor getPlayerTourDetails(String tournament,int matchnum,String player){
        //getting our readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //creating a cursor
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+TOURNAMENT_COL+"=? AND "+MATCH_COL+"=? AND "+PLAYER_COL+"=?",new String[]{tournament,Integer.toString(matchnum),player});

        cursor.moveToFirst();

        return  cursor;
    }
    public Cursor getTeamDetails(String tournament,int k){
        //getting our readable database
        SQLiteDatabase db = this.getReadableDatabase();
        char tempchar='A';
        tempchar+=k;
        String player="Player"+tempchar;
        //creating a cursor
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+TOURNAMENT_COL+"=? AND "+PLAYER_COL+"=?",new String[]{tournament,player});

        cursor.moveToFirst();

        return  cursor;
    }

    public void deleteCourse(String tourname) {

        // creating writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting tournament
        db.delete(TABLE_NAME, TOURNAMENT_COL+"=?", new String[]{tourname});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
