package com.luong.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Activity class for displaying game history using a RecyclerView
public class HistoryActivity extends AppCompatActivity {

    // RecyclerView for displaying the match history list
    private RecyclerView recyclerView;
    // Adapter for the RecyclerView to bind Match data to the view
    private MatchAdapter adapter;
    // List to store the history of past matches
    private List<Match> pastMatches; // Declare here as an instance variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the layout defined in 'activity_history.xml'
        setContentView(R.layout.activity_history);

        // Initialize the 'Clear History' button
        Button clearHistoryButton = findViewById(R.id.clearButton);
        // Initialize the 'Back' button
        Button backButton = findViewById(R.id.backButton);

        // Set an OnClickListener to clear the game history when the clear button is clicked
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of GameHistory to interact with SharedPreferences
                GameHistory gameHistory = new GameHistory(getApplicationContext());
                // Clear the stored match history
                gameHistory.clearHistory();

                // Clear the current list of past matches and notify the adapter of the change
                pastMatches.clear();
                adapter.notifyDataSetChanged();
            }
        });

        // Set an OnClickListener to finish the activity when the back button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity, returning to the previous screen
                finish();
            }
        });

        // Find the RecyclerView in the layout
        recyclerView = findViewById(R.id.recyclerView);

        // Instantiate GameHistory to get the list of past matches
        GameHistory gameHistory = new GameHistory(this);
        // Retrieve the list of past matches from SharedPreferences
        pastMatches = gameHistory.getPastMatches();

        // Initialize the adapter with the list of past matches
        adapter = new MatchAdapter(pastMatches);
        // Set the layout manager for the RecyclerView to linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }
}



