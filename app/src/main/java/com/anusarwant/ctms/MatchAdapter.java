package com.anusarwant.ctms;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Match> matchArrayList;
    private Context mContext;

    // creating a constructor for our variables.
    public MatchAdapter(ArrayList<Match> matchArrayList, Context mContext) {
        this.matchArrayList = matchArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Match modal = matchArrayList.get(position);
        holder.team1NameTV.setText(modal.team1.name);
        holder.matchNumTV.setText("Match " + Integer.toString(modal.matchNum));
        holder.team2NameTV.setText(modal.team2.name);
        holder.team1score.setText(modal.team1score);
        holder.team2score.setText(modal.team2score);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modal.isDone) {
                    // Start New Activity
                }
                else {
                    Toast.makeText(mContext,"Match not played yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return matchArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView team1NameTV, matchNumTV, team2NameTV, team1score, team2score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            team1NameTV = itemView.findViewById(R.id.idTVTeam1Name);
            matchNumTV = itemView.findViewById(R.id.idTVMatchNumber);
            team2NameTV = itemView.findViewById(R.id.idTVTeam2Name);
            team1score = itemView.findViewById(R.id.team1Score);
            team2score = itemView.findViewById(R.id.team2Score);
        }
    }
}
