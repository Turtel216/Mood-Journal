package com.dimitrios_papakonstantinou.mood_journal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserIdNotFoundException.class})
    public ResponseEntity<Object> handleUserIdNotFoundException
            (UserIdNotFoundException userIdNotFoundException)
    {
        UserException userException = new UserException(
                userIdNotFoundException.getMessage(),
                userIdNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(userException, HttpStatus.NOT_FOUND);
    }
}
