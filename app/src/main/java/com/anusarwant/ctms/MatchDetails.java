package com.anusarwant.ctms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MatchDetails extends AppCompatActivity {
    int matchNum;
    String tourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        getSupportActionBar().setTitle("Match Details");

        Intent intent = getIntent();
        matchNum = intent.getIntExtra("matchNum",-1);
        tourName = intent.getStringExtra("tourName");

        DBHandler db=new DBHandler(this);
        Button in1 = findViewById(R.id.innings1);
        in1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor=db.getDetails(tourName,matchNum+1);
                if(cursor.getCount()==0){

                    Toast.makeText(MatchDetails.this,"No details found",Toast.LENGTH_SHORT).show();
                }
                else{
                    int score=0,total=0,wickets=0;
                    int cnt=0;
                    StringBuffer buffer1 = new StringBuffer();
                    buffer1.append("TEAM-"+cursor.getString(2).charAt(6)+"\n\n");
                    do {
                        if(cnt==11)break;
                        if(cursor.getString(4).equals("0")){
                            buffer1.append("Batsman : "+cursor.getString(2)+"\n");
                            buffer1.append("Did Not Bat\n\n\n");

                        }
                        else{
                            buffer1.append("Batsman : "+cursor.getString(2)+"\n");
                            buffer1.append("Runs Scored : "+cursor.getString(3)+"\n");
                            buffer1.append("Balls Played : "+cursor.getString(4)+"\n");
                            buffer1.append("Fours : "+cursor.getString(5)+"\n");
                            buffer1.append("Sixes : "+cursor.getString(6)+"\n");
                            buffer1.append("Strike Rate : "+cursor.getString(7)+"\n\n");
                            score+=cursor.getInt(3);
                        }

                        cnt++;
                    }while(cursor.moveToNext());

                    cnt=0;
                    while(cnt!=6){
                        cursor.moveToNext();
                        cnt++;
                    }
                    StringBuffer buffer2 = new StringBuffer();
                    buffer2.append("TEAM-"+cursor.getString(2).charAt(6)+"\n\n");
                    do {
                            String overs=cursor.getString(9);
                            int tmpint1=Integer.parseInt(overs);
                            int tmpint2=tmpint1/6;
                            overs=String.valueOf(tmpint2)+"."+String.valueOf(tmpint1%6);

                            buffer2.append("Bowler : "+cursor.getString(2)+"\n");
                            buffer2.append("Wickets Taken : "+cursor.getString(8)+"\n");
                            buffer2.append("Overs Bowled : "+overs+"\n");
                            buffer2.append("Runs Given : "+cursor.getString(10)+"\n");
                            buffer2.append("Economy : "+cursor.getDouble(11)+"\n\n\n");
                            cnt++;
                            wickets+=cursor.getInt(8);
                            total+=cursor.getInt(10);
                    }while(cursor.moveToNext());
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(buffer1);
                    buffer.append("Total : "+total+"/"+wickets+"\n");
                    buffer.append("Extras: "+(total-score)+"\n\n\n");
                    buffer.append(buffer2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MatchDetails.this);
                    builder.setCancelable(true);
                    builder.setTitle("Innings 1 Summary");
                    builder.setMessage(buffer.toString());
                    builder.show();

                }
            }

        });
        Button in2 = findViewById(R.id.innings2);
        in2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor=db.getDetails(tourName,matchNum+1);
                if(cursor.getCount()==0){

                    Toast.makeText(MatchDetails.this,"No details found",Toast.LENGTH_SHORT).show();
                }
                else{
                    int score=0,total=0,wickets=0;
                    int cnt=0;
                    while(cnt!=11){
                        cursor.moveToNext();
                        cnt++;
                    }
                    StringBuffer buffer1 = new StringBuffer();
                    buffer1.append("TEAM-"+cursor.getString(2).charAt(6)+"\n\n");
                    do {
                        if(cursor.getString(4).equals("0")){
                            buffer1.append("Batsman : "+cursor.getString(2)+"\n");
                            buffer1.append("Did Not Bat\n\n\n");

                        }
                        else{
                            buffer1.append("Batsman : "+cursor.getString(2)+"\n");
                            buffer1.append("Runs Scored : "+cursor.getString(3)+"\n");
                            buffer1.append("Balls Played : "+cursor.getString(4)+"\n");
                            buffer1.append("Fours : "+cursor.getString(5)+"\n");
                            buffer1.append("Sixes : "+cursor.getString(6)+"\n");
                            buffer1.append("Strike Rate : "+cursor.getString(7)+"\n\n");
                            score+=cursor.getInt(3);
                        }

                        cnt++;
                    }while(cursor.moveToNext());

                    cnt=0;
                    cursor=db.getDetails(tourName,matchNum+1);
                    while(cnt!=6){
                        cursor.moveToNext();
                        cnt++;
                    }
                    StringBuffer buffer2 = new StringBuffer();
                    buffer2.append("TEAM-"+cursor.getString(2).charAt(6)+"\n\n");
                    do {
                        String overs=cursor.getString(9);
                        int tmpint1=Integer.parseInt(overs);
                        int tmpint2=tmpint1/6;
                        overs=String.valueOf(tmpint2)+"."+String.valueOf(tmpint1%6);

                        buffer2.append("Bowler : "+cursor.getString(2)+"\n");
                        buffer2.append("Wickets Taken : "+cursor.getString(8)+"\n");
                        buffer2.append("Overs Bowled : "+overs+"\n");
                        buffer2.append("Runs Given : "+cursor.getString(10)+"\n");
                        buffer2.append("Economy : "+cursor.getDouble(11)+"\n\n\n");
                        cnt++;
                        wickets+=cursor.getInt(8);
                        total+=cursor.getInt(10);
                    }while(cursor.moveToNext() && cnt<11);
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(buffer1);
                    buffer.append("Total : "+total+"/"+wickets+"\n");
                    buffer.append("Extras: "+(total-score)+"\n\n\n");
                    buffer.append(buffer2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MatchDetails.this);
                    builder.setCancelable(true);
                    builder.setTitle("Innings 2 Summary");
                    builder.setMessage(buffer.toString());
                    builder.show();

                }
            }

        });

    }
}