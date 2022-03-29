package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

public class CreateNewTournament extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public int noTeams, noOvers;

    // variable for our adapter class and array list
    private ArrayList<Tournament> tournamentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tournament);
        getSupportActionBar().setTitle("New Tournament");

        Button generate = findViewById(R.id.genrate_tour_schedule);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_newTournament);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout_new_tournament);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner num_teams = findViewById(R.id.spinnerNumTeams);
        Spinner num_overs = findViewById(R.id.spinnerNumOvers);

        num_teams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noTeams = Integer.parseInt(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(CreateNewTournament.this,"Select Number of Teams",Toast.LENGTH_SHORT).show();
            }
        });

        num_overs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                noOvers = Integer.parseInt(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(CreateNewTournament.this,"Select Number of Overs", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tourNameEditText = (EditText) findViewById(R.id.tournamentName);
                String tourName = tourNameEditText.getText().toString();

                if (tourName.equals("")){
                    Toast.makeText(CreateNewTournament.this, "Tournament name cannot be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Character.isWhitespace(tourName.charAt(0)) || Character.isWhitespace(tourName.charAt(tourName.length()-1))){
                    return;
                }
                // Check if tournament name is unique
                for (int i = 0; i<tournamentArrayList.size(); i++){
                    if (tourName.equals(tournamentArrayList.get(i).name)){
                        Toast.makeText(CreateNewTournament.this, "Tournament with same name already exits!\nTry a new Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                // Create new tournament
                Tournament newTour = new Tournament(noOvers,noTeams,tourName);

                //Create teams
                for (int i = 0; i<noTeams; i++){
                    char x='A';
                    x+=i;
                    Team newTeam = new Team("Team-"+x);

                    //Create players (captain etc too)
                    for (int j=0; j<11; j++){
                        int age = (int) (Math.random()*25)+18;
                        Random random = new Random();
                        double prob = random.nextFloat();
                        if (prob<0.4){
                            prob=-1;
                        }
                        else if (0.4<prob && prob<0.6){
                            prob=0;
                        }
                        else prob=-1;
                        Player newPlayer = new Player("Player"+x+(j+1),age,random.nextFloat()>0.2,(int)prob);

                        //Add player to team
                        newTeam.playersList.add(newPlayer);
                    }
                    // Sort Players according to batting bowling
                    Collections.sort(newTeam.playersList,Player.playerComparator);

                    // Add team to tournament
                    newTour.teamsArray.add(newTeam);
                }

                Vector<Integer> matchNumbers = new Vector<Integer>();
                for (int i = 0; i< noTeams*(noTeams-1)/2; i++){
                    matchNumbers.add(i);
                }
                Collections.shuffle(matchNumbers);
                int counter=0;

                // Now the teams have been created
                // Generate Schedule of tournament
                for (int i=0; i<noTeams; i++){
                    for (int j=i+1; j<noTeams; j++) {
                        Team team1 = newTour.teamsArray.get(i);
                        Team team2 = newTour.teamsArray.get(j);
                        Match newMatch = new Match(team1,team2);
                        newMatch.matchNum=matchNumbers.get(counter++)+1;
                        newMatch.tournamentName=newTour.name;
                        newTour.matchesArray.add(newMatch);
                    }
                }
                Collections.sort(newTour.matchesArray,Match.matchComparator);

                // Add tournament to arrayList
                tournamentArrayList.add(newTour);

                // Store arrayList in shared Preferences
                saveData();
            }
        });
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

        Type type = new TypeToken<ArrayList<Tournament>>() {}.getType();

        tournamentArrayList = gson.fromJson(json,type);

        // checking if array list is null
        if (tournamentArrayList==null) {
            // create new array list
            tournamentArrayList = new ArrayList<>();

        }
    }

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
        String json = gson.toJson(tournamentArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("tournaments", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Toast.makeText(CreateNewTournament.this, "Tournament Generated", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(CreateNewTournament.this, MainActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.new_tournament_nav: {
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.prev_tour_details_nav: {
                Intent i = new Intent(CreateNewTournament.this,ViewPreviousTournamentDetails.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.stats_nav: {
                Intent i = new Intent(CreateNewTournament.this, ViewStatistics.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.settings_nav: {
                Intent i = new Intent(CreateNewTournament.this, AppSettings.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }

        return true;
    }

}