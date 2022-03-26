package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class CreateNewTournament extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public int noTeams, noOvers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tournament);
        getSupportActionBar().setTitle("New Tournament");

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
                Toast.makeText(CreateNewTournament.this,"Number of Teams Selected",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CreateNewTournament.this, "Number of Overs Selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(CreateNewTournament.this,"Select Number of Overs", Toast.LENGTH_SHORT).show();
            }
        });
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