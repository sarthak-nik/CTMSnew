package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TeamList extends AppCompatActivity {

    private RecyclerView teamRV;

    // variable for our adapter class and array list
    private TeamAdapter adapter;
    private ArrayList<Tournament> tournamentArrayList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        getSupportActionBar().setTitle("Teams");

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);

        teamRV = findViewById(R.id.idRVTeams);

        // calling method to load data
        // from shared prefs.
        loadData();

        // calling method to build
        // recycler view.
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new TeamAdapter(tournamentArrayList.get(position).teamsArray, TeamList.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        teamRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        teamRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        teamRV.setAdapter(adapter);
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
        tournamentArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (tournamentArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            tournamentArrayList = new ArrayList<>();
        }
    }
}