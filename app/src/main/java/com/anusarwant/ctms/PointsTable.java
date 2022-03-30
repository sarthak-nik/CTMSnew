package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PointsTable extends AppCompatActivity {
    int position;
    private ArrayList<Tournament> tournamentArrayList;
    private ArrayList<Team> teamsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        getSupportActionBar().setTitle("Points Table");

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);

        loadData();

        TextView heading = findViewById(R.id.tableHeader);
        heading.setText(tournamentArrayList.get(position).name);

        teamsList=new ArrayList<Team>();
        for(int i=0;i<tournamentArrayList.get(position).teamsArray.size();i++){
            teamsList.add(tournamentArrayList.get(position).teamsArray.get(i));
            teamsList.get(i).wins=0;
            teamsList.get(i).losses=0;
            teamsList.get(i).draws=0;
            teamsList.get(i).points=0;
        }

        for(int i=0;i<tournamentArrayList.get(position).matchesArray.size();i++){
            String winner=tournamentArrayList.get(position).matchesArray.get(i).winner;
            if(winner.equals("Match Drawn")){
                teamsList.get(tournamentArrayList.get(position).matchesArray.get(i).team1.name.charAt(5)-'A').draws++;
                teamsList.get(tournamentArrayList.get(position).matchesArray.get(i).team2.name.charAt(5)-'A').draws++;
                continue;
            }
            teamsList.get(winner.charAt(5)-'A').wins++;
        }
        for(int i=0;i<teamsList.size();i++){
            teamsList.get(i).losses=teamsList.size()-1-teamsList.get(i).wins-teamsList.get(i).draws;
            teamsList.get(i).points=2*teamsList.get(i).wins+teamsList.get(i).draws;
        }
        Collections.sort(teamsList,Team.teamComparator);
        TableLayout tableLayout=findViewById(R.id.pointsTable);
        TableRow tr1;
        TextView t1,t2,t3,t4,t5,t6;
        for (int i =0;i<teamsList.size();i++){
            tr1 = new TableRow(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t3 = new TextView(this);
            t4 = new TextView(this);
            t5 = new TextView(this);
            t6 = new TextView(this);
            t1.setText(Integer.toString(i+1));
            t2.setText(teamsList.get(i).name);
            t3.setText(Integer.toString(teamsList.get(i).wins));
            t4.setText(Integer.toString(teamsList.get(i).losses));
            t5.setText(Integer.toString(teamsList.get(i).draws));
            t6.setText(Integer.toString(teamsList.get(i).points));
            t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tr1.setPadding(0,8,0,8);

            tr1.addView(t1);
            tr1.addView(t2);
            tr1.addView(t3);
            tr1.addView(t4);
            tr1.addView(t5);
            tr1.addView(t6);
            tableLayout.addView(tr1);
        }

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
    }
}