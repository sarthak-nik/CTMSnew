package com.anusarwant.ctms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    //creating constant variables for databases
    private static final String DB_NAME = "ctmsdb";
    private static final int DB_VERSION = 1;
    //creating variable for table name
    private static final String TABLE_NAME = "stats";
    //creating variable for tournament name column
    private static final String TOURNAMENT_COL = "tournament";
    //creating variable for match number column
    private static final String MATCH_COL = "match";
    //creating variable for innings number column
    private static final String INNINGS_COL = "innings";
    //creating variable for player name column
    private static final String PLAYER_COL = "player";
    //creating variable for runs column
    private static final String RUNS_COL = "runs_scored";
    //creating variable for balls played column
    private static final String BALLS_COL = "ballsPlayed";
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
                + MATCH_COL + "INTEGER,"
                + INNINGS_COL + " INTEGER,"
                + PLAYER_COL + " INTEGER,"
                + RUNS_COL + "INTEGER,"
                + BALLS_COL + "INTEGER,"
                + STRIKE_COL + "REAL,"
                + BATAVG_COL + "REAL,"
                + WICKETS_COL + "INTEGER,"
                + BALLSBOWLED_COL + "INTEGER,"
                + RUNSGIVEN_COL + "INTEGER,"
                + ECONOMY_COL + "REAL,"
                + BOWLAVG_COL + "REAL)";


        db.execSQL(query);
    }
    public void addNewRow(String tournament,int match,int innings,int player,int runs,int ballsPlayed,
                          float strikeRate,float batAvg,int ballsBowled,int wickets,int runsGiven,float economy,float bowlAvg)
    {

        //getting writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        // variable for content values.
        ContentValues values = new ContentValues();

        //storing all values
        values.put(TOURNAMENT_COL,tournament);
        values.put(MATCH_COL,match);
        values.put(INNINGS_COL,innings);
        values.put(PLAYER_COL,player);
        values.put(RUNS_COL,runs);
        values.put(BALLS_COL,ballsPlayed);
        values.put(STRIKE_COL,strikeRate);
        values.put(BATAVG_COL,batAvg);
        values.put(BALLSBOWLED_COL,ballsBowled);
        values.put(WICKETS_COL,wickets);
        values.put(ECONOMY_COL,economy);
        values.put(BOWLAVG_COL,bowlAvg);

        //adding content values to the table
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our database
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
