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
}
