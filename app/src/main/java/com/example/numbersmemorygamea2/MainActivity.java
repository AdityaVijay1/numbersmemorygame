package com.example.numbersmemorygamea2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tv_level ,tv_number;
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
        et_number = findViewById(R.id.et_number);
        confirm = findViewById(R.id.b_confirm);

        // Display the current level
        tv_level.setText("Level : " + currentlevel);

        rand = new Random();

        //Hide the Input and the Button and show the number
        et_number.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
        tv_number.setVisibility(View.VISIBLE);

        //Display random numbers according to levels
        generatedNumber = generatenumber(currentlevel);
        tv_number.setText(generatedNumber);

        //Display the elements after a second and Hide the number
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                et_number.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
                tv_number.setVisibility(View.GONE);

                et_number.requestFocus();

            }
        },1500);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(generatedNumber.equals(et_number.getText().toString())) {

                    et_number.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    tv_number.setVisibility(View.VISIBLE);

                    // Remove text, once moved to next level
                    et_number.setText("");

                    // Increase the current level
                    currentlevel++;

                    // Increase the score
                    score++;

                    // Display the current level
                    tv_level.setText("Level : " + currentlevel);

                    //Display random numbers according to levels
                    generatedNumber = generatenumber(currentlevel);
                    tv_number.setText(generatedNumber);

                    //Display values
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_number.setVisibility(View.VISIBLE);
                            confirm.setVisibility(View.VISIBLE);
                            tv_number.setVisibility(View.GONE);

                            et_number.requestFocus();

                        }
                    },1500);
                }
                // End the game if the value entered is wrong
                else {
                    tv_level.setText("Game Over!! The Number was " + generatedNumber);
                    confirm.setEnabled(false);
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("Score",score);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    // Generate a number for the game
    public String generatenumber(int digits) {
        String output = "";
        for (int i=0;i<digits;i++) {
            int randomDigit = rand.nextInt(10);
            output = output + "" + randomDigit;
        }
        return output;
    }
}