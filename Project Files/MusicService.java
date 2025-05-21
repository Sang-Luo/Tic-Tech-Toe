package com.luong.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.app.Service;


public class MusicService extends Service {
    // MediaPlayer instance to handle music play
    private MediaPlayer mediaPlayer;

    // This method is required to be overridden but is not used in this service as it's not bound
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // onCreate is called when the service is first created
    @Override
    public void onCreate() {
        super.onCreate();
        // Create a MediaPlayer to play the music file puzzle_game_2_looping from the raw resource folder
        mediaPlayer = MediaPlayer.create(this, R.raw.puzzle_game_2_looping);
        mediaPlayer.setLooping(true); // Set the music to loop continuously
        mediaPlayer.setVolume(100, 100); // Set the volume. The max value for MediaPlayer.setVolume is 1.0f, so this will need to be adjusted.
    }

    // onStartCommand is called every time a client starts the service using startService(Intent intent)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start(); // Start the media player
        return START_STICKY; // Service will be restarted if it gets terminated
    }

    // onDestroy is called when the service is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop(); // Stop the music if it is playing
            }
            mediaPlayer.release(); // Release resources used by the MediaPlayer
            mediaPlayer = null; // Nullify the MediaPlayer reference
        }
    }

    // onLowMemory is called when the Android system requests that the process trim its memory usage
    @Override
    public void onLowMemory() {
        // It's not necessary to implement anything here for this service
    }
}
