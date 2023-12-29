package com.example.numbersmemorygamea2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


// Class definition for HomeActivity
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find buttons by ID
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnHighScores = findViewById(R.id.btnHighScores);
        Button btnOptions = findViewById(R.id.btnOptions);

        // Set click listeners for each button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to SignUp Page
                startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to Login Page
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to High Score Page
                startActivity(new Intent(HomeActivity.this, HighScoreActivity.class));
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to Background Music/ Options Page
                startActivity(new Intent(HomeActivity.this, OptionsActivity.class));
            }
        });
    }
}
