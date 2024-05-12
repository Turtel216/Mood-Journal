package com.dimitrios_papakonstantinou.mood_journal.exceptions;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(String message) {
        super(message);
    }

    public UserIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
