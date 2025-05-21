package com.luong.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultDialog extends Dialog {

    private Button mainMenuButton; // The button to return to the main menu

    private final String message; // The message to be displayed in the dialog
    private final MainActivity mainActivity; // A reference to the MainActivity to call restartMatch method

    // Constructor for the ResultDialog class
    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message; // Set the message to display
        this.mainActivity = mainActivity; // Set the reference to the MainActivity
    }

    // This method is called when the dialog is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog); // Set the content from a layout resource

        // Find the TextView by its ID and set the message
        TextView messageText = findViewById(R.id.messageText);
        messageText.setText(message);

        mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();  // Dismiss the dialog
                Intent intent = new Intent(getContext(), StartingScreen.class);
                getContext().startActivity(intent);
            }
        });

        Button startAgainButton = findViewById(R.id.startAgainButton);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restartMatch(); // Call the restartMatch method in MainActivity
                dismiss(); // Dismiss the dialog
            }
        });
    }
}
