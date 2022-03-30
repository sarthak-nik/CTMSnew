package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class ViewMatchList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView courseRV;

    // variable for our adapter class and array list
    private MatchAdapter adapter;
    private ArrayList<Tournament> courseModalArrayList;
    int position;

    // database handler object
    public  DBHandler db;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ViewMatchList.this,ViewPreviousTournamentDetails.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match_list);
        getSupportActionBar().setTitle("Match List");

        // Get data from previous intent
        Intent intent = getIntent();
        position = intent.getIntExtra("tourObjPosition",-1);
        courseRV = findViewById(R.id.idRVMatches);

        // Set Navigation bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_match_list);
        navigationView.setNavigationItemSelectedListener(this);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout_view_match_list);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // calling method to load data
        // from shared prefs.
        loadData();

        // calling method to build
        // recycler view.
        buildRecyclerView();

        //creating database object
        db=new DBHandler(this);

        // Implement topFive Buttons onClickListener
        Button topFive = findViewById(R.id.topFive);
        topFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!courseModalArrayList.get(position).iscomplete){
                    Toast.makeText(ViewMatchList.this,"The Tournament has not been played yet.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(ViewMatchList.this,TopFives.class);
                i.putExtra("tourNum",position);
                startActivity(i);
            }
        });

        //Implement points Buttons onClickListener
        Button points = findViewById(R.id.pointsTable);
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!courseModalArrayList.get(position).iscomplete){
                    Toast.makeText(ViewMatchList.this,"The Tournament has not been played yet.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(ViewMatchList.this,PointsTable.class);
                i.putExtra("tourNum",position);
                startActivity(i);
            }
        });

        Button teamDetails = findViewById(R.id.teamDetails);
        teamDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!courseModalArrayList.get(position).iscomplete){
                    Toast.makeText(ViewMatchList.this,"The Tournament has not been played yet.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(ViewMatchList.this, TeamList.class);
                i.putExtra("tourNum", position);
                startActivity(i);
            }
        });

        Button deleteTour = findViewById(R.id.deletetour);
        deleteTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteTournament(courseModalArrayList.get(position).name);
                courseModalArrayList.remove(position);
                saveData();
                Intent i=new Intent(ViewMatchList.this,ViewPreviousTournamentDetails.class);
                startActivity(i);
            }
        });


        Button play = findViewById(R.id.playTour);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(courseModalArrayList.get(position).iscomplete){
                    Toast.makeText(ViewMatchList.this,"This Tournament has already been played.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Random rd = new Random();
                int target;
                for(int i =0;i<courseModalArrayList.get(position).matchesArray.size();i++){
                    // Toss
                    Random r = new Random();
                    if (r.nextBoolean()){
                        courseModalArrayList.get(position).matchesArray.get(i).toss=courseModalArrayList.get(position).matchesArray.get(i).team1.name;
                    }
                    else {
                        courseModalArrayList.get(position).matchesArray.get(i).toss = courseModalArrayList.get(position).matchesArray.get(i).team2.name;
                    }
                    // Play a match
                    if (rd.nextBoolean()){
                        courseModalArrayList.get(position).matchesArray.get(i).battedFirst=courseModalArrayList.get(position).matchesArray.get(i).team1.name;
                        target=playFirstInnings(i,courseModalArrayList.get(position).matchesArray.get(i).team1,courseModalArrayList.get(position).matchesArray.get(i).team2,1);
                        playSecondInnings(target,i,courseModalArrayList.get(position).matchesArray.get(i).team2,courseModalArrayList.get(position).matchesArray.get(i).team1,1);
                        addToDatabase(i,courseModalArrayList.get(position).matchesArray.get(i).team1);
                        addToDatabase(i,courseModalArrayList.get(position).matchesArray.get(i).team2);
                    }
                    else{
                        courseModalArrayList.get(position).matchesArray.get(i).battedFirst=courseModalArrayList.get(position).matchesArray.get(i).team2.name;
                        target=playFirstInnings(i,courseModalArrayList.get(position).matchesArray.get(i).team2,courseModalArrayList.get(position).matchesArray.get(i).team1,2);
                        playSecondInnings(target,i,courseModalArrayList.get(position).matchesArray.get(i).team1,courseModalArrayList.get(position).matchesArray.get(i).team2,2);
                        addToDatabase(i,courseModalArrayList.get(position).matchesArray.get(i).team2);
                        addToDatabase(i,courseModalArrayList.get(position).matchesArray.get(i).team1);
                    }
                    courseModalArrayList.get(position).matchesArray.get(i).isDone=true;

                }

                for(int i=0;i<courseModalArrayList.get(position).teamsArray.size();i++)
                {
                    for(int j=0;j<11;j++)
                    {
                        Cursor cursor=db.getPlayerDetails(courseModalArrayList.get(position).name,courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).name);
                        if(cursor.getCount()==0){
                            Toast.makeText(ViewMatchList.this,"No details found",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            do{
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsScored+=cursor.getInt(3);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsPlayed+=cursor.getInt(4);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourFours+=cursor.getInt(5);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourSixes+=cursor.getInt(6);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourWickets+=cursor.getInt(8);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsBowled+=cursor.getInt(9);
                                courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsGiven+=cursor.getInt(10);
                            }while(cursor.moveToNext());

                            db.addNewRow(courseModalArrayList.get(position).name,
                                    -1,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).name,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsScored,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsPlayed,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourFours,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourSixes,
                                    Math.floor((courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsScored*100.0)/courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsPlayed*100)/100.0,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsBowled,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourWickets,
                                    courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsGiven,
                                    Math.floor((courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourRunsGiven*6.0)/courseModalArrayList.get(position).teamsArray.get(i).playersList.get(j).tourBallsBowled*100)/100.0
                            );
                        }
                    }
                }
                courseModalArrayList.get(position).status="Completed";
                courseModalArrayList.get(position).iscomplete=true;
                saveData();

                finish();
                startActivity(getIntent());
            }
        });
    }

    // Add Data to sql database
    private void addToDatabase(int k,Team team1) {
        for (int i =0; i<11;i++){
            // Update tournament stats of the player
            team1.playersList.get(i).tourBallsPlayed+=team1.playersList.get(i).matchBallsPlayed;
            team1.playersList.get(i).tourRunsScored+=team1.playersList.get(i).matchRunsScored;
            team1.playersList.get(i).tourFours+=team1.playersList.get(i).matchFours;
            team1.playersList.get(i).tourSixes+=team1.playersList.get(i).matchSixes;
            team1.playersList.get(i).tourBallsBowled+=team1.playersList.get(i).matchBallsBowled;
            team1.playersList.get(i).tourWickets+=team1.playersList.get(i).matchWicketsTaken;
            team1.playersList.get(i).tourRunsGiven+=team1.playersList.get(i).matchRunsGiven;

            //Add Match details to database
            double strkRate=team1.playersList.get(i).matchRunsScored*100.0/team1.playersList.get(i).matchBallsPlayed;
            double ecn=team1.playersList.get(i).matchRunsGiven/(team1.playersList.get(i).matchBallsBowled/6.0);
            strkRate=Math.floor(strkRate * 100) / 100.0;
            ecn=Math.floor(ecn*100)/100.0;
            db.addNewRow(courseModalArrayList.get(position).name,
                    k+1,
                    team1.playersList.get(i).name,
                    team1.playersList.get(i).matchRunsScored,
                    team1.playersList.get(i).matchBallsPlayed,
                    team1.playersList.get(i).matchFours,
                    team1.playersList.get(i).matchSixes,
                    strkRate,
                    team1.playersList.get(i).matchBallsBowled,
                    team1.playersList.get(i).matchWicketsTaken,
                    team1.playersList.get(i).matchRunsGiven,
                    ecn
            );
            //Reset match stats of the player
            team1.playersList.get(i).matchBallsPlayed=0;
            team1.playersList.get(i).matchRunsScored=0;
            team1.playersList.get(i).matchFours=0;
            team1.playersList.get(i).matchSixes=0;
            team1.playersList.get(i).matchBallsBowled=0;
            team1.playersList.get(i).matchWicketsTaken=0;
            team1.playersList.get(i).matchRunsGiven=0;
        }
    }

    // Play Second Innings ball by ball
    private void playSecondInnings(int target,int i, Team battingTeam, Team bowlingTeam, int bat){
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
                else if(ballOutcome<=0.6){
                    //one run
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.84){
                    //two runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=2;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=2;
                    totalRuns+=2;

                }
                else if(ballOutcome<=0.87){
                    //three runs
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=3;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=3;
                    totalRuns+=3;

                    // Swapping onStrike and nonStrike batsman
                    onStrike += (nonStrike-(nonStrike=onStrike));

                }
                else if(ballOutcome<=0.90){
                    //four
                    battingTeam.playersList.get(onStrike).matchBallsPlayed++;
                    bowlingTeam.playersList.get(bowler).matchBallsBowled++;

                    battingTeam.playersList.get(onStrike).matchRunsScored+=4;
                    battingTeam.playersList.get(onStrike).matchFours++;
                    bowlingTeam.playersList.get(bowler).matchRunsGiven+=4;
                    totalRuns+=4;
                }
                else if(ballOutcome<=0.93){
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
                    battingTeam.playersList.get(onStrike).tourOuts++;
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
                    courseModalArrayList.get(position).matchesArray.get(i).winner=battingTeam.name;
                    battingTeam.points+=2;
                    battingTeam.matchHistory.add(1);
                    bowlingTeam.matchHistory.add(-1);

                    String teamScore = Integer.toString(totalRuns) + "/" + Integer.toString(innningWickets) + " ("+ Integer.toString(over)+"."+Integer.toString(balls+1)+")";
                    if (bat==1){
                        courseModalArrayList.get(position).matchesArray.get(i).team2score= teamScore;
                    }
                    else {
                        courseModalArrayList.get(position).matchesArray.get(i).team1score= teamScore;
                    }

                    return;
                }
                if (innningWickets==10 && totalRuns !=(target-1)){
                    battingTeam.losses++;
                    bowlingTeam.wins++;
                    courseModalArrayList.get(position).matchesArray.get(i).winner=bowlingTeam.name;
                    bowlingTeam.points+=2;
                    bowlingTeam.matchHistory.add(1);
                    battingTeam.matchHistory.add(-1);

                    String teamScore = Integer.toString(totalRuns) + "/" + Integer.toString(innningWickets) + " ("+ Integer.toString(over)+"."+Integer.toString(balls+1)+")";
                    if (bat==1){
                        courseModalArrayList.get(position).matchesArray.get(i).team2score= teamScore;
                    }
                    else {
                        courseModalArrayList.get(position).matchesArray.get(i).team1score= teamScore;
                    }
                    return;
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

        String teamScore = Integer.toString(totalRuns) + "/" + Integer.toString(innningWickets) + " ("+ Integer.toString(courseModalArrayList.get(position).nOvers)+".0)";
        if (bat==1){
            courseModalArrayList.get(position).matchesArray.get(i).team2score= teamScore;
        }
        else {
            courseModalArrayList.get(position).matchesArray.get(i).team1score= teamScore;
        }

        // If Match Drawn
        if (totalRuns==(target-1)){
            battingTeam.draws++;
            bowlingTeam.draws++;
            battingTeam.points++;
            bowlingTeam.points++;
            battingTeam.matchHistory.add(0);
            bowlingTeam.matchHistory.add(0);
            courseModalArrayList.get(position).matchesArray.get(i).winner="Match Drawn";
            return;
        }

        battingTeam.losses++;
        bowlingTeam.wins++;
        battingTeam.matchHistory.add(-1);
        bowlingTeam.matchHistory.add(1);
        courseModalArrayList.get(position).matchesArray.get(i).winner=bowlingTeam.name;
        bowlingTeam.points+=2;
    }

    // Play First Innings ball by ball
    private int playFirstInnings(int i, Team battingTeam, Team bowlingTeam,int bat){
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
                    battingTeam.playersList.get(onStrike).tourOuts++;
                    onStrike=innningWickets+1;
                }
                else{
                    //wide
                    bowlingTeam.playersList.get(bowler).matchRunsGiven++;
                    totalRuns++;
                    balls--;
                }

                if (innningWickets==10){
                    String teamScore = Integer.toString(totalRuns) + "/" + Integer.toString(innningWickets) + " ("+ Integer.toString(over)+"."+Integer.toString(balls+1)+")";
                    if (bat==1){
                        courseModalArrayList.get(position).matchesArray.get(i).team1score= teamScore;
                    }
                    else {
                        courseModalArrayList.get(position).matchesArray.get(i).team2score= teamScore;
                    }
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
        String teamScore = Integer.toString(totalRuns) + "/" + Integer.toString(innningWickets) + " ("+ Integer.toString(courseModalArrayList.get(position).nOvers)+".0)";
        if (bat==1){
            courseModalArrayList.get(position).matchesArray.get(i).team1score= teamScore;
        }
        else {
            courseModalArrayList.get(position).matchesArray.get(i).team2score= teamScore;
        }
        return totalRuns+1;
    }

    // Build Recycler View
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

    // Function to load Data
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

    // Function to save Data
    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(courseModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("tournaments", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks
        switch (item.getItemId()) {
            case R.id.home_nav: {
                Intent i = new Intent(ViewMatchList.this, MainActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.new_tournament_nav: {
                Intent i = new Intent(ViewMatchList.this, CreateNewTournament.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.prev_tour_details_nav: {
                Intent i = new Intent(ViewMatchList.this,ViewPreviousTournamentDetails.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }
}