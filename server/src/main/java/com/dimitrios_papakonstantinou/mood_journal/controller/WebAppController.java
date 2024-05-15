package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.response.ResponseHandler;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moodjournal")
@AllArgsConstructor
public class WebAppController {

    private WebAppService webAppService;

    @PostMapping("/entry")
    public ResponseEntity<Object> addEntry(@RequestBody Entry entry) {
        webAppService.saveEntry(entry);

        return ResponseHandler.responseBuilder("Entry saved successfully", HttpStatus.OK, entry);
    }

    // saveEntry throws exception on userId not found and returns an updated NotFound response to client
    @GetMapping("/entry/{userId}")
    public ResponseEntity<Object> getEntry(@PathVariable("userId") Long userId) {
        var currentDate = "05.05.2024";
        var entry = webAppService.getEntry(userId, currentDate);

        return ResponseHandler.responseBuilder("Requested entry", HttpStatus.OK, entry);
    }
}
