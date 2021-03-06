package com.anusarwant.ctms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import java.util.Collections;

public class TopFives extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Tournament> tournamentArrayList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fives);
        getSupportActionBar().setTitle("Tournament Top Five's");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_top_five);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout_top_five);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);
        // calling method to load data
        // from shared prefs.
        loadData();

        ArrayList<Player> playerList=new ArrayList<Player>();
        DBHandler db=new DBHandler(this);
        // reading from database
        Cursor cursor=db.getDetails(tournamentArrayList.get(position).name,-1);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"No Details Found!!",Toast.LENGTH_SHORT);
        }
        else{
            do{
                //storing required stats for every player
                Player player=new Player(cursor.getString(2),-1,true,-1);
                player.tourRunsScored=cursor.getInt(3);
                player.tourWickets=cursor.getInt(8);
                player.tourStrikeRate=Math.floor(cursor.getDouble(7)*100)/100.0;
                player.tourEconomy=Math.floor(cursor.getDouble(11)*100)/100.0;
                player.tourFours=cursor.getInt(5);
                player.tourSixes= cursor.getInt(6);
                playerList.add(player);
            }while (cursor.moveToNext());
        }

        Button hRuns=findViewById(R.id.hRunScorer);
        //Button to display top 5 runs scorer
        hRuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playersRunsComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");

                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Runs Scored: "+playerList.get(i).tourRunsScored+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Highest Run Scorers");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Button hWicks=findViewById(R.id.hWickTaker);
        // Button to display top 5 wickets
        hWicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerWicketsComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Wickets Taken: "+playerList.get(i).tourWickets+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Highest Wicket Takers");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Button hStrk=findViewById(R.id.hStrikeRate);
        //Button to display top 5 players with highest strike rate
        hStrk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerStrikeRateComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Strike Rate: "+playerList.get(i).tourStrikeRate+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Highest Strike Rate");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Button hEcn=findViewById(R.id.hEconomy);
        // Button to display top 5 players with best economy
        hEcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerEconomyComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                int cnt=0;
                while(playerList.get(cnt).tourEconomy<0.1)cnt++;
                for(int i=cnt;i<cnt+5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i-cnt+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Economy: "+playerList.get(i).tourEconomy+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Best Economy");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Button hFour=findViewById(R.id.hFours);
        // button to display top 5 players with most number of fours
        hFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerFoursComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Number of Fours: "+playerList.get(i).tourFours+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Most Fours");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Button hSix=findViewById(R.id.hSixes);
        // button to display top 5 players with most number of sixes
        hSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerSixesComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
                    buffer.append("Player Name: "+playerList.get(i).name+"\n");
                    buffer.append("    Number of Sixes: "+playerList.get(i).tourSixes+"\n");
                    buffer.append("    Team Name: TEAM-"+playerList.get(i).name.charAt(6)+"\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(TopFives.this);
                builder.setCancelable(true);
                builder.setTitle("Most Sixes");
                builder.setMessage(buffer.toString());
                builder.show();
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
                Intent i = new Intent(TopFives.this, MainActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.new_tournament_nav: {
                Intent i = new Intent(TopFives.this,CreateNewTournament.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case  R.id.prev_tour_details_nav: {
                Intent i = new Intent(TopFives.this,ViewPreviousTournamentDetails.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return true;
    }
}