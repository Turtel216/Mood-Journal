package com.dimitrios_papakonstantinou.mood_journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntryExceptionHandler {

    @ExceptionHandler(value = {EntryIdAndEntryDateNotFoundException.class})
    public ResponseEntity<Object> EntryIdAndEntryDateNotFoundException
            (EntryIdAndEntryDateNotFoundException entryIdAndEntryDateNotFoundException)
    {
        EntryException entryException = new EntryException(
                entryIdAndEntryDateNotFoundException.getMessage(),
                entryIdAndEntryDateNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(entryException, HttpStatus.NOT_FOUND);
    }
}
