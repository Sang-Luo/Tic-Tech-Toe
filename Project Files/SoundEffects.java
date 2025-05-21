package com.luong.tictactoe;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundEffects {

    private MediaPlayer mediaPlayer;

    // Constructor
    public SoundEffects(Context context) {
        // Initialize media player with the sound effect from the raw resource folder
        mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);
    }

    // Play the sound effect
    public void playClickSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start(); // Start the media player to play the sound effect
        }
    }

    // Release the media player resources
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the resources associated with the MediaPlayer
            mediaPlayer = null; // Set the mediaPlayer object to null
        }
    }
}
