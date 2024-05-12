package com.dimitrios_papakonstantinou.mood_journal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class EntryException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
