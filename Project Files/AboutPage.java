package com.luong.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

// AboutPage Activity to display the about page of the Tic-Tech-Toe game
public class AboutPage extends AppCompatActivity {

    // Declaration of the back button
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_about_page.xml layout file
        setContentView(R.layout.activity_about_page);

        // Find the ImageView from the layout
        ImageView gifImageView = findViewById(R.id.animatedGifImageView);
        // Glide to load a GIF into the ImageView. The R.drawable.ttt_animated is a resource id for gif
        Glide.with(this).asGif().load(R.drawable.ttt_animated).into(gifImageView);

        // Initialize the back button from the layout
        backButton = findViewById(R.id.backButton);

        // Set an onClickListener on the back button to respond to user clicks
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close the AboutPage activity and return to the previous activity in the stack
                finish();
            }
        });
    }
}

