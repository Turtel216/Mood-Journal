package com.dimitrios_papakonstantinou.mood_journal.exceptions;

public class EntryIdAndEntryDateNotFoundException extends RuntimeException{
    public EntryIdAndEntryDateNotFoundException(String message) {
        super(message);
    }

    public EntryIdAndEntryDateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
