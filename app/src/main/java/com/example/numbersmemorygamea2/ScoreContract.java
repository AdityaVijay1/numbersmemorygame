package com.example.numbersmemorygamea2;


import android.provider.BaseColumns;

// Class definition for ScoreContract
public class ScoreContract {
    // Make the class non-instantiable with a private constructor
    private ScoreContract() {
    }

    // Inner class defining the table columns by implementing BaseColumns interface
    public static final class ScoreEntry implements BaseColumns {
        // Table name constant
        public static final String TABLE_NAME = "high_scores";

        // Column names constants
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
    }
}




