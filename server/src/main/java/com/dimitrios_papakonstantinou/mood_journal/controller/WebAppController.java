package com.dimitrios_papakonstantinou.mood_journal.controller;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.service.implementation.WebAppServiceImplementation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moodjournal")
public class WebAppController {

    private WebAppServiceImplementation webAppService;

    @PostMapping("/entry")
    public String addEntry(@RequestBody Entry entry) {
        return webAppService.saveEntry(entry);
    }

    @GetMapping("/entry/{userId}")
    public Entry getEntry(@PathVariable Long userId) {
        var entry = webAppService.getEntry(userId);
        return entry != null ? entry : null; //TODO replace null with proper response
    }
}
