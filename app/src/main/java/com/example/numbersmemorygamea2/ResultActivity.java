package com.example.numbersmemorygamea2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        try {
            // Retrieve the score from the intent
            int score = getIntent().getIntExtra("Score", 0);

            // Display the score in the TextView
            TextView tvResult = findViewById(R.id.tvresult);
            tvResult.setText("Your Score: " + score);

            // Get the current Firebase user
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Retrieve the player name from Firestore
                FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
                dbFirestore.collection("users")
                        .document(currentUser.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String playerName = document.getString("name");

                                        // Log the retrieved player name
                                        Log.d("ResultActivity", "PlayerName: " + playerName);

                                        // Insert the score into the SQLite database
                                        ScoreDbHelper dbHelper = new ScoreDbHelper(ResultActivity.this);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(ScoreContract.ScoreEntry.COLUMN_NAME, playerName);
                                        values.put(ScoreContract.ScoreEntry.COLUMN_SCORE, score);

                                        long newRowId = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
                                        db.close();

                                        Button highscoreBtn = findViewById(R.id.highscoreBtn);
                                        highscoreBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Handle the click event to navigate to the HighScoreActivity
                                                Intent intent = new Intent(ResultActivity.this, HighScoreActivity.class);
                                                startActivity(intent);
                                            }
                                        });

                                        Button shareBtn = findViewById(R.id.shareBtn);
                                        shareBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Handle the click event to open Instagram.com
                                                String url = "https://www.instagram.com/";
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(android.net.Uri.parse(url));
                                                startActivity(intent);
                                            }
                                        });

                                        Button restartBtn = findViewById(R.id.restartBtn);
                                        restartBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Handle the click event to restart MainActivity
                                                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
