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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    // creating a variable for array list and context.
    private ArrayList<Player> playerArrayList;
    private Context mContext;

    // creating a constructor for our variables.
    public PlayerAdapter(ArrayList<Player> playerArrayList, Context mContext) {
        this.playerArrayList = playerArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Player modal = playerArrayList.get(position);
        holder.playerNameTV.setText(modal.name);

        // Set text views appropriately
        String tmp="Batsman";
        if(modal.role==0)tmp="All-Rounder";
        if(modal.role==1)tmp="Spin Bowler";
        if(modal.role==2)tmp="Fast Bowler";
        if(modal.isRight)tmp="Right-Handed "+tmp;
        else tmp="Left-Handed "+tmp;
        holder.roleTV.setText("Role: "+tmp);
        holder.ageTV.setText("Age: "+modal.age);
        holder.runScoredTV.setText("Runs Scored: "+modal.tourRunsScored);
        holder.strikeRateTV.setText("Strike Rate: "+modal.tourStrikeRate);
        holder.foursTV.setText("Fours: "+modal.tourFours);
        holder.sixesTV.setText("Sixes: "+modal.tourSixes);
        int balls=modal.tourBallsBowled/6;
        holder.oversBowledTV.setText("Overs Bowled: "+Integer.toString(balls)+"."+Integer.toString(modal.tourBallsBowled%6));
        holder.wicketsTakenTV.setText("Wickets Taken: "+modal.tourWickets);
        holder.economyTV.setText("Economy: "+modal.tourEconomy);

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return playerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView playerNameTV,roleTV,ageTV,runScoredTV,strikeRateTV,foursTV,sixesTV,oversBowledTV,wicketsTakenTV,economyTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            playerNameTV = itemView.findViewById(R.id.idTVPlayerName);
            roleTV=itemView.findViewById(R.id.role);
            ageTV=itemView.findViewById(R.id.idTVPlayerAge);
            runScoredTV=itemView.findViewById(R.id.runsScored);
            strikeRateTV=itemView.findViewById(R.id.strikeRate);
            foursTV=itemView.findViewById(R.id.fours);
            sixesTV=itemView.findViewById(R.id.sixes);
            oversBowledTV=itemView.findViewById(R.id.oversBowled);
            wicketsTakenTV=itemView.findViewById(R.id.wicketsTaken);
            economyTV=itemView.findViewById(R.id.economy);

        }
    }
}
