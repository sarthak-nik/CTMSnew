package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class AppSettings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        getSupportActionBar().setTitle("Settings");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_appSettings);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout_settings);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent i = new Intent(AppSettings.this, MainActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.new_tournament_nav: {
                Intent i = new Intent(AppSettings.this,CreateNewTournament.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.prev_tour_details_nav: {
                Intent i = new Intent(AppSettings.this,ViewPreviousTournamentDetails.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.stats_nav: {
                Intent i = new Intent(AppSettings.this, ViewStatistics.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.settings_nav: {
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }

        return true;
    }
}