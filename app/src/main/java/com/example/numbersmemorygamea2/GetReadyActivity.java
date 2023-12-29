package com.example.numbersmemorygamea2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


// Class definition for GetReadyActivity
public class GetReadyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ready);

        TextView tvTimer = findViewById(R.id.tv_timer);

        // Start a 5-second countdown timer
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Update the timer text with remaining seconds
                tvTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                // Timer finished, start the main game activity
                Intent intent = new Intent(GetReadyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
