package com.dimitrios_papakonstantinou.mood_journal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MoodException {
    private final String message;
    private final Throwable throwable;
}
