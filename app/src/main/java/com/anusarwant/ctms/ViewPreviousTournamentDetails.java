package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewPreviousTournamentDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private RecyclerView tournamentRV;

    // variable for our adapter class and array list
    private TournamentAdapter adapter;
    private ArrayList<Tournament> tournamentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_tournament_details);
        getSupportActionBar().setTitle("Tournament Details");

        tournamentRV = findViewById(R.id.idRVCourses);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_viewPrevTourDetails);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout_viewTour);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load Data
        loadData();

        // Build Recycler View
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new TournamentAdapter(tournamentArrayList, ViewPreviousTournamentDetails.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        tournamentRV.setHasFixedSize(true);
        tournamentRV.setLayoutManager(manager);
        tournamentRV.setAdapter(adapter);

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
        //Temp textview
        TextView tempText=findViewById(R.id.temp_textView);
        // checking if array list is null
        if (tournamentArrayList==null) {
            // create new array list
            tournamentArrayList = new ArrayList<>();
        }
        else{
            tempText.setVisibility(View.INVISIBLE);

        }
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
                Intent i = new Intent(ViewPreviousTournamentDetails.this, MainActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.new_tournament_nav: {
                Intent i = new Intent(ViewPreviousTournamentDetails.this, CreateNewTournament.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.prev_tour_details_nav: {
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            
        }

        return true;
    }

}