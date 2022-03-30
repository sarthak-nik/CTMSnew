package com.anusarwant.ctms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class TopFives extends AppCompatActivity {
    ArrayList<Tournament> tournamentArrayList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fives);
        getSupportActionBar().setTitle("Tournament Top Five's");

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);
        // calling method to load data
        // from shared prefs.
        loadData();

        ArrayList<Player> playerList=new ArrayList<Player>();
        DBHandler db=new DBHandler(this);
        Cursor cursor=db.getDetails(tournamentArrayList.get(position).name,-1);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"No Details Found!!",Toast.LENGTH_SHORT);
        }
        else{
            do{
                Player player=new Player(cursor.getString(2),-1,true,-1);
                player.tourRunsScored=cursor.getInt(3);
                player.tourWickets=cursor.getInt(8);
                player.tourStrikeRate=cursor.getDouble(7);
                player.tourEconomy=cursor.getDouble(11);
                player.tourFours=cursor.getInt(5);
                player.tourSixes= cursor.getInt(6);
                playerList.add(player);
            }while (cursor.moveToNext());
        }

        Button hRuns=findViewById(R.id.hRunScorer);
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
        hEcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(playerList,Player.playerEconomyComparator);
                StringBuffer buffer=new StringBuffer();
                buffer.append("\n");
                for(int i=0;i<5 && i<playerList.size();i++)
                {
                    buffer.append(Integer.toString(i+1)+". ");
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
                    buffer.append("    Number of Sixes: "+playerList.get(i).tourFours+"\n");
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
}