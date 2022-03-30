package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlayerDetails extends AppCompatActivity {

    private RecyclerView playerRV;

    // variable for our adapter class and array list
    private PlayerAdapter adapter;
    private ArrayList<Tournament> tournamentArrayList;

    int position;
    int teamNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        getSupportActionBar().setTitle("Player Details");

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);
        teamNum = intent.getIntExtra("teamNum",-1);

        playerRV = findViewById(R.id.idRVPlayers);

        // calling method to load data
        // from shared prefs.
        loadData();

        DBHandler db=new DBHandler(this);

        int hRun=0,hWick=10;
        int runs=0,wicks=0;
        for(int j=0;j<11;j++)
        {
                Cursor cursor=db.getPlayerTourDetails(tournamentArrayList.get(position).name,-1,tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).name);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourRunsScored=cursor.getInt(3);
                if(cursor.getInt(3)>runs){
                    runs=cursor.getInt(3);
                    hRun=j;
                }
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourBallsPlayed=cursor.getInt(4);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourFours=cursor.getInt(5);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourSixes=cursor.getInt(6);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourStrikeRate=Math.floor(cursor.getDouble(7)*100)/100.0;
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourWickets=cursor.getInt(8);
                if(cursor.getInt(8)>wicks){
                    wicks=cursor.getInt(8);
                    hWick=j;
                }
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourBallsBowled=cursor.getInt(9);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourRunsGiven=cursor.getInt(10);
                tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(j).tourEconomy=Math.floor(cursor.getDouble(11)*100)/100.0;
        }
        String history="";
        int nWins=0;
        for(int i=tournamentArrayList.get(position).matchesArray.size()-1;i>=0;i--){
            if(tournamentArrayList.get(position).matchesArray.get(i).team1.name.equals(tournamentArrayList.get(position).teamsArray.get(teamNum).name) ||
                    tournamentArrayList.get(position).matchesArray.get(i).team2.name.equals(tournamentArrayList.get(position).teamsArray.get(teamNum).name)){
                if(tournamentArrayList.get(position).matchesArray.get(i).winner.equals(tournamentArrayList.get(position).teamsArray.get(teamNum).name)){
                    nWins++;
                    history+="W ";
                }
                else history+="L ";
            }
        }
        if(history.length()>10){
            history=history.substring(0,10);
        }
        TextView t1=findViewById(R.id.tStats1);
        t1.setText(tournamentArrayList.get(position).teamsArray.get(teamNum).name + " Stats");
        t1=findViewById(R.id.teamMatchesPlayed);
        t1.setText("Matches Played: "+Integer.toString(tournamentArrayList.get(position).teamsArray.size()-1));
        t1=findViewById(R.id.teamMatchesWon);
        t1.setText("Matches Won: "+Integer.toString(nWins));
        t1=findViewById(R.id.matchHistory);
        t1.setText("Matches History: "+history);
        t1=findViewById(R.id.teamHighestRunScorer);
        t1.setText("Highest Run Scorer: "+tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(hRun).name+" ("+runs+")");
        t1=findViewById(R.id.teamHighestWicketTaker);
        t1.setText("Highest Wicket Taker: "+tournamentArrayList.get(position).teamsArray.get(teamNum).playersList.get(hWick).name+" ("+wicks+")");
        // calling method to build
        // recycler view.
        buildRecyclerView();

    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new PlayerAdapter(tournamentArrayList.get(position).teamsArray.get(teamNum).playersList, PlayerDetails.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        playerRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        playerRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        playerRV.setAdapter(adapter);
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("tournaments", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<Tournament>>() {
        }.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        tournamentArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (tournamentArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            tournamentArrayList = new ArrayList<>();
        }
    }
}