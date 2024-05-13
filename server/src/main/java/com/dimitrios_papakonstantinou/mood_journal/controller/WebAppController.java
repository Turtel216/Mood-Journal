package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.response.ResponseHandler;
import com.dimitrios_papakonstantinou.mood_journal.service.implementation.WebAppServiceImplementation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moodjournal")
@AllArgsConstructor
@NoArgsConstructor
public class WebAppController {

    private WebAppServiceImplementation webAppService;

    @PostMapping("/entry")
    public String addEntry(@RequestBody Entry entry) {
        return webAppService.saveEntry(entry);
    }

    // saveEntry throws exception on uerId not found and returns updated NotFound response to client
    @GetMapping("/entry/{userId}")
    public ResponseEntity<Object> getEntry(@PathVariable Long userId) {
        var currentDate = "05.05.2024";
        var entry = webAppService.getEntry(userId, currentDate);

        return ResponseHandler.responseBuilder("Requested entry", HttpStatus.OK, entry);
    }
}
