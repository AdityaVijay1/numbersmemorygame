package com.example.numbersmemorygamea2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// ... Existing imports ...

public class ScoreDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 1;

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table for high scores
        String SQL_CREATE_SCORES_TABLE = "CREATE TABLE " +
                ScoreContract.ScoreEntry.TABLE_NAME + " (" +
                ScoreContract.ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScoreContract.ScoreEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ScoreContract.ScoreEntry.COLUMN_SCORE + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // This method will be triggered when the version number is increased
    }
}
