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

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Tournament> courseModalArrayList;
    public Context mContext;

    // creating a constructor for our variables.
    public TournamentAdapter(ArrayList<Tournament> courseModalArrayList, Context mContext) {
        this.courseModalArrayList = courseModalArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TournamentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Tournament modal = courseModalArrayList.get(position);
        holder.courseNameTV.setText(modal.name);
        holder.courseDescTV.setText(modal.status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ViewMatchList.class);
                intent.putExtra("tourObjPosition", holder.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView courseNameTV, courseDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
        }
    }
}

