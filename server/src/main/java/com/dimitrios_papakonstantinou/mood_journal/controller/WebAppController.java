package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.response.ResponseHandler;
import com.dimitrios_papakonstantinou.mood_journal.service.implementation.WebAppServiceImplementation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moodjournal")
public class WebAppController {

    private WebAppServiceImplementation webAppService;

    @PostMapping("/entry")
    public String addEntry(@RequestBody Entry entry) {
        return webAppService.saveEntry(entry);
    }

    //TODO let getEntry throw exception instead of returning null
    @GetMapping("/entry/{userId}")
    public ResponseEntity<Object> getEntry(@PathVariable Long userId) {
        var entry = webAppService.getEntry(userId);

        if(entry == null) {
            return ResponseHandler.responseBuilder("Failed to fetch data", HttpStatus.NOT_FOUND, entry);
        }

        return ResponseHandler.responseBuilder("Requested entry", HttpStatus.OK, entry);
    }
}
