package com.dimitrios_papakonstantinou.mood_journal.exceptions;

public class EntryCouldNotBeSavedException extends RuntimeException{
    public EntryCouldNotBeSavedException(String message) {
        super(message);
    }

    public EntryCouldNotBeSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
