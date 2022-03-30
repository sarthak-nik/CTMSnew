package com.anusarwant.ctms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TopFives extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_fives);
        getSupportActionBar().setTitle("Tournament Top Five's");

        Intent intent = getIntent();
        position = intent.getIntExtra("tourNum",-1);
    }
}