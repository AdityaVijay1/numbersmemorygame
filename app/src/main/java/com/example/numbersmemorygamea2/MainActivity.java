package com.example.numbersmemorygamea2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv_level, tv_number, tv_score; // Added tv_score
    EditText et_number;
    Button confirm;

    Random rand;

    String generatedNumber;

    int currentlevel = 1;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get attribute values
        tv_level = findViewById(R.id.tv_level);
        tv_number = findViewById(R.id.tv_number);
        tv_score = findViewById(R.id.tv_score); // Added tv_score
        et_number = findViewById(R.id.et_number);
        confirm = findViewById(R.id.b_confirm);

        // Display the current level
        tv_level.setText("Level : " + currentlevel);
        // Display the current score
        tv_score.setText("Your Current Score: " + score);

        rand = new Random();

        // Hide the Input and the Button and show the number
        et_number.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
        tv_number.setVisibility(View.VISIBLE);

        // Display random numbers according to levels
        generatedNumber = generatenumber(currentlevel);
        tv_number.setText(generatedNumber);

        // Set touch listener for the TextView
        tv_number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle touch down event
                        handleTouchDown();
                        break;


                }
                return true;
            }
        });

        // Display the elements after a second and Hide the number
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                et_number.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);

                et_number.requestFocus();
            }
        }, 1500);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (generatedNumber.equals(et_number.getText().toString())) {

                    et_number.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    tv_number.setVisibility(View.VISIBLE);

                    // Remove text, once moved to the next level
                    et_number.setText("");

                    // Increase the current level
                    currentlevel++;

                    // Increase the score
                    score++;

                    // Display the current level and score
                    tv_level.setText("Level : " + currentlevel);
                    tv_score.setText("Your Current Score: " + score);

                    // Display random numbers according to levels
                    generatedNumber = generatenumber(currentlevel);
                    tv_number.setText(generatedNumber);

                    // Display values
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_number.setVisibility(View.VISIBLE);
                            confirm.setVisibility(View.VISIBLE);
                            tv_number.setVisibility(View.GONE);

                            et_number.requestFocus();
                        }
                    }, 1500);
                }
                // End the game if the value entered is wrong
                else {
                    tv_level.setText("Game Over!! The Number was " + generatedNumber);
                    confirm.setEnabled(false);
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("Score", score);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Generate a number for the game
    public String generatenumber(int digits) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            int randomDigit = rand.nextInt(10);
            output.append(randomDigit);
        }
        return output.toString();
    }

    // Handle touch down event
    private void handleTouchDown() {
        // Display a Toast message when the number is touched
        Toast.makeText(MainActivity.this, "Touch detected on the number", Toast.LENGTH_SHORT).show();

    }
}
