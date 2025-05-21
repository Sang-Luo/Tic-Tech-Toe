package com.luong.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// MatchAdapter class is an adapter for a RecyclerView that displays a list of matches.
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    // A list to hold Match objects that this adapter will display
    private List<Match> matches;

    // Constructor to create an instance of MatchAdapter with a given list of matches
    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
    }

    // onCreateViewHolder is called by the RecyclerView to create new ViewHolder instances.
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflates the XML layout for an individual item in the list
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        // Returns a new instance of MatchViewHolder with the inflated view
        return new MatchViewHolder(itemView);
    }

    // onBindViewHolder is called to display the data at the specified position.
    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        // Gets the match object from the list based on position
        Match match = matches.get(position);
        // Binds the match data to the respective TextViews in our ViewHolder
        holder.winnerName.setText(match.getWinnerName());
        holder.result.setText(match.getResult());
        holder.playerOneName.setText("Player One: " + match.getPlayerOneName());
        holder.playerTwoName.setText("Player Two: " + match.getPlayerTwoName());
    }

    // Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        // The size of the matches list determines the number of items
        return matches.size();
    }

    // A static inner class that represents the ViewHolder pattern. Each object holds a reference
    // to all the widgets in a single RecyclerView item.
    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        // TextViews to display the winner name, result, and player names
        public TextView winnerName, result, playerOneName, playerTwoName;
        // Add other UI components if necessary...

        // Constructor accepts the entire item row and does the view lookups to find each subview
        public MatchViewHolder(View view) {
            super(view);
            // Initialize the TextViews by finding them by their ID in the layout
            winnerName = view.findViewById(R.id.winnerName);
            result = view.findViewById(R.id.result);
            playerOneName = view.findViewById(R.id.playerOneName);
            playerTwoName = view.findViewById(R.id.playerTwoName);
            // Initialize other UI components if necessary...
        }
    }
}


