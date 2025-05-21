package com.luong.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.concurrent.atomic.AtomicBoolean;

// AddPlayers Activity to handle player name input and game settings before starting the game
public class AddPlayers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_add_players.xml layout file
        setContentView(R.layout.activity_add_players);

        // Define EditText fields for player names and CheckBoxes for game options
        EditText playerOne = findViewById(R.id.playerOne);
        EditText playerTwo = findViewById(R.id.playerTwo);
        CheckBox botCheckBox = findViewById(R.id.botCheckBox);
        CheckBox isEasyMode = findViewById(R.id.isEasyMode);
        Button startGameButton = findViewById(R.id.startGameButton);

        // Set listener for isEasyMode checkbox to disable playerTwo input and botCheckBox if easy mode is selected
        isEasyMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Disable input fields for playerTwo and botCheckBox when Easy Mode is on
                botCheckBox.setEnabled(false);
                playerTwo.setEnabled(false);
            } else {
                // Enable input fields for playerTwo and botCheckBox when Easy Mode is off
                botCheckBox.setEnabled(true);
                playerTwo.setEnabled(true);
                playerTwo.setText(""); // Clear text field when Easy Mode is deselected
            }
        });

        // Set listener for botCheckBox to disable playerTwo input and isEasyMode if bot is selected
        botCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Disable isEasyMode CheckBox and playerTwo input field when Bot is selected
                isEasyMode.setEnabled(false);
                isEasyMode.setChecked(false); // Uncheck isEasyMode when Bot is selected
                playerTwo.setEnabled(false);
            } else {
                // Enable isEasyMode CheckBox and playerTwo input field when Bot is not selected
                isEasyMode.setEnabled(true);
                playerTwo.setEnabled(true);
                playerTwo.setText(""); // Clear text field when Bot is deselected
            }
        });

        // Add TextWatcher to playerTwo EditText to disable botCheckBox when playerTwo is being typed
        playerTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // Disable botCheckBox when there is text in playerTwo field
                    botCheckBox.setEnabled(false);
                } else if (!isEasyMode.isChecked()) {
                    // Enable botCheckBox when playerTwo field is empty and Easy Mode is not selected
                    botCheckBox.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set listener for startGameButton to collect player names and start the game
        startGameButton.setOnClickListener(view -> {
            // Trim to remove any leading or trailing whitespace
            String getPlayerOneName = playerOne.getText().toString().trim();
            String getPlayerTwoName;

            // Determine the name of player two or the bot based on the user's selections
            if (botCheckBox.isChecked()) {
                getPlayerTwoName = "Normal Bot"; // Name for normal difficulty bot
            } else if (isEasyMode.isChecked()) {
                getPlayerTwoName = "Easy Bot"; // Name for easy difficulty bot
            } else {
                getPlayerTwoName = playerTwo.getText().toString().trim(); // Name entered by user
            }

            // Validation and Toast messages to ensure player names are entered or game mode is selected
            if (getPlayerOneName.isEmpty()) {
                Toast.makeText(AddPlayers.this, "Please enter Player 1's name", Toast.LENGTH_SHORT).show();
            } else if (getPlayerTwoName.isEmpty()) {
                Toast.makeText(AddPlayers.this, "Please enter Player 2's name or select a Bot option", Toast.LENGTH_SHORT).show();
            } else {
                // Intent to start the main game activity with player names and game options as extras
                Intent intent = new Intent(AddPlayers.this, MainActivity.class);
                intent.putExtra("playerOne", getPlayerOneName);
                intent.putExtra("playerTwo", getPlayerTwoName);
                // Pass whether a bot is being used and the difficulty level
                intent.putExtra("playWithBot", botCheckBox.isChecked() || isEasyMode.isChecked());
                intent.putExtra("isEasyMode", isEasyMode.isChecked());
                startActivity(intent); // Start MainActivity with the given settings
            }
        });
    }
}
