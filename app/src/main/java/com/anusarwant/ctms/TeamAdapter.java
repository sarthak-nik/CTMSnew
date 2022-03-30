package com.anusarwant.ctms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    // creating a variable for array list and context.
    private ArrayList<Team> TeamArrayList;
    private Context mContext;

    // creating a constructor for our variables.
    public TeamAdapter(ArrayList<Team> TeamArrayList, Context mContext) {
        this.TeamArrayList = TeamArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Team modal = TeamArrayList.get(position);
        holder.teamNameTV.setText(modal.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,PlayerDetails.class);
                intent.putExtra("teamNum", holder.getAdapterPosition());
                intent.putExtra("tourNum", modal.tourPosition);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return TeamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView teamNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            teamNameTV = itemView.findViewById(R.id.idTVteamName);
        }
    }

}
