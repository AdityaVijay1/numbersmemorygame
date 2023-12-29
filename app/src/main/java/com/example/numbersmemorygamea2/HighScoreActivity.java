package com.example.numbersmemorygamea2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Initialize database helper
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser != null ? currentUser.getUid() : null;

        // Get the locally stored player's name from SQLite database
        String playerName = getLocalPlayerName();

        // Query the database to get the high scores
        Cursor cursor = db.query(
                ScoreContract.ScoreEntry.TABLE_NAME,
                new String[]{ScoreContract.ScoreEntry.COLUMN_NAME, ScoreContract.ScoreEntry.COLUMN_SCORE},
                null,
                null,
                null,
                null,
                ScoreContract.ScoreEntry.COLUMN_SCORE + " DESC",
                "10"
        );

        // Prepare data for the ListView
        ArrayList<String> highScoresList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String playerNameFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_NAME));
            int playerScore = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_SCORE));

            // Checking if playerNameFromCursor is not null before adding to the list
            if (playerNameFromCursor != null) {
                highScoresList.add(playerNameFromCursor + " - " + playerScore);
            }
        }


        cursor.close();
        db.close();

        // Populate ListView with high scores
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HighScoreActivity.this, android.R.layout.simple_list_item_1, highScoresList);
        listView.setAdapter(adapter);

        // Find the restart button by ID
        Button restartBtn = findViewById(R.id.restartBtn);

        // Set OnClickListener for the restart button
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to restart the main activity
                Intent intent = new Intent(HighScoreActivity.this, GetReadyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Find the home button by ID
        Button homeBtn = findViewById(R.id.homeBtn);

        // Set OnClickListener for the home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to go back to the home activity
                Intent intent = new Intent(HighScoreActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method to get locally stored player's name from SQLite database
    private String getLocalPlayerName() {
        String playerName = "";
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to get the locally stored player's name
        Cursor cursor = db.query(
                ScoreContract.ScoreEntry.TABLE_NAME,
                new String[]{ScoreContract.ScoreEntry.COLUMN_NAME},
                null,
                null,
                null,
                null,
                null
        );

        // Retrieve the player's name from the cursor
        if (cursor.moveToFirst()) {
            playerName = cursor.getString(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_NAME));
        }


        cursor.close();
        db.close();

        return playerName;
    }
}
