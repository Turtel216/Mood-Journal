package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.response.ResponseHandler;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO Update Testing

@RestController
@RequestMapping("/api/moodjournal")
@AllArgsConstructor
public class WebAppController {

    private WebAppService webAppService;

    // saveEntry throws an exception on userId not found and returns an updated NotFound response
    @PostMapping("/entry")
    public ResponseEntity<Object> addEntry(@RequestBody Entry entry) {
        var responeString = webAppService.saveEntry(entry);

        return ResponseHandler.responseBuilder(responeString, HttpStatus.OK, entry);
    }

    // getEntry throws exception on userId/currentDate not found and returns an updated NotFound response to client
    @GetMapping("/entry/{userId}")
    public ResponseEntity<Object> getEntry(@PathVariable("userId") Long userId) {
        var currentDate = "05.05.2024";
        var entry = webAppService.getEntry(userId, currentDate);

        return ResponseHandler.responseBuilder("Requested entry", HttpStatus.OK, entry);
    }

    // getMood throws exception on userId not found and returns an updated NotFound response to client
    @GetMapping("/mood/{userId}")
    public ResponseEntity<Object> getMood(@PathVariable("userId") Long userId) {
        var mood = webAppService.getMood(userId);

        return ResponseHandler.responseBuilder("Mood entries from provided user id", HttpStatus.OK, mood);
    }

    // getMean throws exception on userId not found and returns an updated NotFound response to client
    @GetMapping("/mood/mean/{userId}")
    public ResponseEntity<Object> getMean(@PathVariable("userId") Long userId) {
        var mean = webAppService.getMean(userId);

        return ResponseHandler.responseBuilder(
                "The mean mood of all entries by the user with the request id",
                HttpStatus.OK, mean);
    }
}
