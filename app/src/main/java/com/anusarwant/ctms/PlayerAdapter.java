package com.anusarwant.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return playerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView playerNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            playerNameTV = itemView.findViewById(R.id.idTVPlayerName);
        }
    }
}
