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

public class PointsTable extends AppCompatActivity {
    int position;
    private ArrayList<Tournament> tournamentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);

        loadData();

        TableLayout tableLayout=findViewById(R.id.pointsTable);
        TableRow tr1;
        TextView t1,t2,t3,t4,t5,t6;
        for (int i =0;i<tournamentArrayList.get(position).teamsArray.size();i++){
            tr1 = new TableRow(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t3 = new TextView(this);
            t4 = new TextView(this);
            t5 = new TextView(this);
            t6 = new TextView(this);
            t1.setText(Integer.toString(i+1));
            t2.setText(tournamentArrayList.get(position).teamsArray.get(i).name);
            t3.setText(Integer.toString(tournamentArrayList.get(position).teamsArray.get(i).wins));
            t4.setText(Integer.toString(tournamentArrayList.get(position).teamsArray.get(i).losses));
            t5.setText(Integer.toString(tournamentArrayList.get(position).teamsArray.get(i).draws));
            t6.setText(Integer.toString(tournamentArrayList.get(position).teamsArray.get(i).points));
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