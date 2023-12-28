package com.example.numbersmemorygamea2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.numbersmemorygamea2.MusicService;

public class OptionsActivity extends AppCompatActivity {

    private Button musicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        musicButton = findViewById(R.id.toggleMusicButton);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle music playback using a service
                Intent musicServiceIntent = new Intent(OptionsActivity.this, MusicService.class);
                if (isMusicPlaying()) {
                    // Stop music
                    stopService(musicServiceIntent);
                } else {
                    // Start music
                    startService(musicServiceIntent);
                }
            }
        });

        // Rest of your onCreate code...
    }

    private boolean isMusicPlaying() {
        // Check if the MusicService is running
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MusicService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // Rest of your OptionsActivity code...
}
