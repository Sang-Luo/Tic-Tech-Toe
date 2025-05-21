package com.luong.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class StartingScreen extends AppCompatActivity {

    // Constants for the SharedPreferences file name and the sound setting key
    private static final String PREFS_NAME = "TicTacToePrefs";
    private static final String SOUND_SETTING = "sound_setting";

    // SharedPreferences for storing and retrieving persistent key-value pairs
    private SharedPreferences sharedPreferences;

    // Helper class instance for playing sound effects
    private SoundEffects soundEffects;

    // Intent for managing the music service
    private Intent musicServiceIntent;

    // Method to save the sound setting into SharedPreferences
    private void saveSoundSetting(boolean soundEnabled) {
        sharedPreferences.edit().putBoolean(SOUND_SETTING, soundEnabled).apply();
    }

    // Method to retrieve the sound setting from SharedPreferences
    private boolean getSoundSetting() {
        // Returns the saved sound setting or 'true' if none is saved (default value)
        return sharedPreferences.getBoolean(SOUND_SETTING, true);
    }

    // Lifecycle callback for when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the layout for this activity
        setContentView(R.layout.activity_starting_screen);

        // Initialize the sound effects manager
        soundEffects = new SoundEffects(this);

        // Creating an intent to use for starting the MusicService
        musicServiceIntent = new Intent(this, MusicService.class);

        // Retrieving the 'start game' button from the layout and setting up its listener
        Button startGameButton = findViewById(R.id.startGameButton);
        // Initialize SharedPreferences with the name defined by PREFS_NAME
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Setting the listener for the 'start game' button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start 'AddPlayers' activity when the 'start game' button is pressed
                Intent intent = new Intent(StartingScreen.this, AddPlayers.class);
                startActivity(intent);
            }
        });

        // Setting up the button that shows a popup menu
        Button showPopupMenuButton = findViewById(R.id.showPopupMenuButton);
        // Assign a listener to the popup menu button to handle click events
        showPopupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Method call to show the popup menu when the button is clicked
                showPopupMenu(view);
            }
        });
    }

    // Lifecycle callback for when the activity comes into the foreground
    @Override
    protected void onResume() {
        super.onResume();
        // Start or stop the music service based on the user's sound settings
        if (getSoundSetting()) {
            // If sound is enabled, start the music service
            startService(musicServiceIntent);
        } else {
            // If sound is disabled, stop the music service
            stopService(musicServiceIntent);
        }
    }

    // Method for showing a popup menu, used by the popup menu button's click listener
    private void showPopupMenu(View view) {
        // Creating a PopupMenu attached to the view passed to the method
        PopupMenu popupMenu = new PopupMenu(this, view);
        // Inflate the popup menu with items from the XML file 'open_menu'
        popupMenu.getMenuInflater().inflate(R.menu.open_menu, popupMenu.getMenu());

        // Setting up a listener for item click events in the popup menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handling click events on the popup menu items
                if (item.getItemId() == R.id.menu_game_history) {
                    // Start the 'HistoryActivity' when 'Game History' is clicked
                    startActivity(new Intent(StartingScreen.this, HistoryActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_toggle_sound) {
                    // Toggle the sound setting when 'Toggle Sound' is clicked
                    if (getSoundSetting()) {
                        // If sound is on, turn it off and update SharedPreferences
                        stopService(musicServiceIntent);
                        saveSoundSetting(false);
                    } else {
                        // If sound is off, turn it on and update SharedPreferences
                        startService(musicServiceIntent);
                        saveSoundSetting(true);
                    }
                    // Show a toast message to indicate the sound has been toggled
                    Toast.makeText(StartingScreen.this, "Toggled Sound", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.menu_about) {
                    // Start the 'AboutPage' activity when 'About' is clicked
                    startActivity(new Intent(StartingScreen.this, AboutPage.class));
                    return true;
                }
                // Return false for any unhandled menu items
                return false;
            }
        });
        // Displaying the popup menu
        popupMenu.show();
    }

    // Lifecycle callback for creating the options menu by inflating a menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource 'open_menu' into the given Menu
        getMenuInflater().inflate(R.menu.open_menu, menu);
        return true;
    }

    // Lifecycle callback for when the activity is being destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources associated with the sound effects manager
        soundEffects.release();
        // Note: Stopping the music service should be handled in the onPause() method if needed
    }

    // Handles selection events for options menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which menu item was chosen
        if (item.getItemId() == R.id.menu_game_history) {
            // Start 'HistoryActivity' when the corresponding item is selected
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_toggle_sound) {
            // Toggle sound setting and handle the music service accordingly
            // The actual logic to toggle sound is handled elsewhere, such as in the popup menu listener
            return true;
        } else if (item.getItemId() == R.id.menu_about) {
            // Start 'AboutPage' activity when the corresponding item is selected
            startActivity(new Intent(this, AboutPage.class));
            return true;
        } else {
            // For any other menu item, use the default handling provided by the superclass
            return super.onOptionsItemSelected(item);
        }
    }
}



