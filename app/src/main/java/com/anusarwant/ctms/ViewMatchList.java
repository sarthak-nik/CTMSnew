package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class ViewMatchList extends AppCompatActivity {

    private RecyclerView courseRV;

    // variable for our adapter class and array list
    private MatchAdapter adapter;
    private ArrayList<Tournament> courseModalArrayList;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_list);

        Intent intent = getIntent();
        position = intent.getIntExtra("tourObjPosition",-1);
        if (position==-1){
            Toast.makeText(this,"Position not passed correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        courseRV = findViewById(R.id.idRVMatches);

        // calling method to load data
        // from shared prefs.
        loadData();

        Toast.makeText(this,Integer.toString(position),Toast.LENGTH_SHORT).show();
        // calling method to build
        // recycler view.
        buildRecyclerView();

        Button play = findViewById(R.id.playTour);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rd = new Random();
                int target;
                String winner;
                for(int i =0;i<courseModalArrayList.size();i++){
                    // Play a match
                    if (rd.nextBoolean()){
                        courseModalArrayList.get(position).matchesArray.get(i).battedFirst=courseModalArrayList.get(position).matchesArray.get(i).team1.name;
                        target=playFirstInnings(i,courseModalArrayList.get(position).matchesArray.get(i).team1,courseModalArrayList.get(position).matchesArray.get(i).team2);
                        winner = playSecondInnings(target,i,courseModalArrayList.get(position).matchesArray.get(i).team2,courseModalArrayList.get(position).matchesArray.get(i).team1);
                    }
                    else{
                        courseModalArrayList.get(position).matchesArray.get(i).battedFirst=courseModalArrayList.get(position).matchesArray.get(i).team2.name;
                        target=playFirstInnings(i,courseModalArrayList.get(position).matchesArray.get(i).team2,courseModalArrayList.get(position).matchesArray.get(i).team1);
                        winner = playSecondInnings(target,i,courseModalArrayList.get(position).matchesArray.get(i).team1,courseModalArrayList.get(position).matchesArray.get(i).team2);
                    }
                    // Add match details to database
                }
            }
        });
      }

    private String playSecondInnings(int target,int i, Team battingTeam, Team bowlingTeam){
        Random r= new Random();
        float ballOutcome;
        int innningWickets=0;
        int onStrike=0;
        int nonStrike=1;
        int bowler = 10;
        int totalRuns=0;
        for (int over=0;over<courseModalArrayList.get(position).nOvers;over++){
            for (int balls=0;balls<6;balls++){
                ballOutcome=r.nextFloat();
                if(ballOutcome<=0.26){
                    //dot ball
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;
                }
                else if(ballOutcome<=0.56){
                    //one run
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.76){
                    //two runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=2;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=2;
                    totalRuns+=2;

                }
                else if(ballOutcome<=0.775){
                    //three runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=3;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=3;
                    totalRuns+=3;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.875){
                    //four
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=4;
                    battingTeam.playersList.get(onStrike).matchFours++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=4;
                    totalRuns+=4;
                }
                else if(ballOutcome<=0.925){
                    //six
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=6;
                    battingTeam.playersList.get(onStrike).matchSixes++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=6;
                    totalRuns+=6;
                }
                else if(ballOutcome<=0.96){
                    //wicket
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;
                    bowlingTeam.playersList.get(bowler).matchWicketsTaken++;
                    innningWickets++;
                    onStrike=innningWickets+1;
                }
                else{
                    //wide
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;
                    balls--;
                }
                if(totalRuns>=target){
                    battingTeam.wins++;
                    bowlingTeam.losses++;
                    return battingTeam.name;
                }
                if (innningWickets==10){
                    battingTeam.losses++;
                    bowlingTeam.wins++;
                    return bowlingTeam.name;
                }
            }
            // After every over
            // Swapping onStrike and nonStrike batsman
            onStrike += (nonStrike-(nonStrike=onStrike));
            bowler--;
            if (bowler<6){
                bowler=10;
            }
        }
        battingTeam.losses++;
        bowlingTeam.wins++;
        return bowlingTeam.name;
    }

    private int playFirstInnings(int i, Team battingTeam, Team bowlingTeam){
        Random r= new Random();
        float ballOutcome;
        int innningWickets=0;
        int onStrike=0;
        int nonStrike=1;
        int bowler = 10;
        int totalRuns=0;
        for (int over=0;over<courseModalArrayList.get(position).nOvers;over++){
            for (int balls=0;balls<6;balls++){
                ballOutcome=r.nextFloat();
                if(ballOutcome<=0.26){
                    //dot ball
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;
                }
                else if(ballOutcome<=0.56){
                    //one run
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.76){
                    //two runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=2;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=2;
                    totalRuns+=2;

                }
                else if(ballOutcome<=0.775){
                    //three runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=3;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=3;
                    totalRuns+=3;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.875){
                    //four
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=4;
                    battingTeam.playersList.get(onStrike).matchFours++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=4;
                    totalRuns+=4;
                }
                else if(ballOutcome<=0.925){
                    //six
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=6;
                    battingTeam.playersList.get(onStrike).matchSixes++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=6;
                    totalRuns+=6;
                }
                else if(ballOutcome<=0.96){
                    //wicket
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;
                    bowlingTeam.playersList.get(bowler).matchWicketsTaken++;
                    innningWickets++;
                    onStrike=innningWickets+1;
                }
                else{
                    //wide
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;
                    balls--;
                }

                if (innningWickets==10){
                    return totalRuns+1;
                }
            }
            // After every over
            // Swapping onStrike and nonStrike batsman
            onStrike += (nonStrike-(nonStrike=onStrike));
            bowler--;
            if (bowler<6){
                bowler=10;
            }
        }
        return totalRuns+1;
    }

    private void buildRecyclerView() {
        // initialize adapter class
        adapter = new MatchAdapter(courseModalArrayList.get(position).matchesArray,ViewMatchList.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        courseRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        courseRV.setAdapter(adapter);
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
        Type type = new TypeToken<ArrayList<Tournament>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        courseModalArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = new ArrayList<>();
        }
    }


}