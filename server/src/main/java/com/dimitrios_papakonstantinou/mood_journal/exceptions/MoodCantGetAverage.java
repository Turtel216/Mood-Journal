package com.dimitrios_papakonstantinou.mood_journal.exceptions;

public class MoodCantGetAverage extends RuntimeException{

    public MoodCantGetAverage(String message) {
        super(message);
    }

    public MoodCantGetAverage(String message, Throwable cause) {
        super(message, cause);
    }
}
