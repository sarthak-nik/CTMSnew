package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MatchDetails extends AppCompatActivity {
    int matchNum;
    String tourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        Intent intent = getIntent();
        matchNum = intent.getIntExtra("matchNum",-1);
        tourName = intent.getStringExtra("tourName");


    }
}