package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import org.springframework.stereotype.Service;

@Service
public class WebAppServiceImplementation implements WebAppService {

    public String addEntry(Entry entry) {
        //TODO implements method
        return "Succes";
    }
}
