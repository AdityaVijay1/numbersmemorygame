package com.example.numbersmemorygamea2;

// ScoreContract.java
import android.provider.BaseColumns;

public class ScoreContract {
    private ScoreContract() {
    }

    public static final class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "high_scores";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
    }
}




