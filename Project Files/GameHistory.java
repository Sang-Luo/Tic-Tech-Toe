package com.luong.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Class to handle game history data storage and retrieval using SharedPreferences
public class GameHistory {

    // Constant for the shared preferences file name
    private static final String PREFERENCES_NAME = "game_history";
    // Constant key used to store and retrieve game history data
    private static final String HISTORY_KEY = "past_matches";
    // SharedPreferences instance to save or read the game history
    private SharedPreferences sharedPreferences;

    // Constructor that initializes the SharedPreferences object
    public GameHistory(Context context) {
        // Context.MODE_PRIVATE ensures that the data is only accessible to this app
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // Method to add a match result to the history
    public void addMatch(Match matchData) {
        // Retrieve the existing match history
        List<Match> pastMatches = getPastMatches();
        // Add the new match data to the history list
        pastMatches.add(matchData);
        // Save the updated list back to SharedPreferences
        saveMatches(pastMatches);
    }

    // Method to clear all match history data
    public void clearHistory() {
        // Remove the history data from SharedPreferences
        sharedPreferences.edit().remove(HISTORY_KEY).apply();
    }

    // Method to retrieve the list of past matches from SharedPreferences
    public List<Match> getPastMatches() {
        // Get the JSON string of past matches, or an empty list if none exists
        String json = sharedPreferences.getString(HISTORY_KEY, "[]");
        // Use Gson to parse the JSON string back into a List of Match objects
        Gson gson = new Gson();
        Type type = new TypeToken<List<Match>>(){}.getType();
        // Convert the JSON string back to a List<Match> object
        return gson.fromJson(json, type);
    }

    // Method to save a list of matches to SharedPreferences
    private void saveMatches(List<Match> matches) {
        // Create an instance of Gson to convert objects to JSON
        Gson gson = new Gson();
        // Convert the list of Match objects into a JSON string
        String json = gson.toJson(matches);
        // Save the JSON string containing all the match histories into SharedPreferences
        sharedPreferences.edit().putString(HISTORY_KEY, json).apply();
    }
}
