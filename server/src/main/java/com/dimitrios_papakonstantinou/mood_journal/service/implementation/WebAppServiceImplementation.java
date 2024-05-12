package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebAppServiceImplementation implements WebAppService {

    private EntryRepository entryRepository;
    private UserRepository userRepository;

    //TODO add findByUserId and findByDate
    public String saveEntry(Entry entry) {
        if(userRepository.findById(entry.getUserId()).isPresent()) {
            try {
                entryRepository.save(entry);
                return "Success";
            } catch(Exception e){
                //TODO throw proper exception
                return "Failure" + e.toString();
            }
        }
        return "User ID not found";
    }

    public Entry getEntry(Long userId) {
        var currentDate = "05.10.2024"; //TODO get real date
        if(entryRepository.findByEntryDateAndUserId(currentDate, userId).isPresent()) {
            return entryRepository.findByEntryDateAndUserId(currentDate, userId).get();
        }

        return null;
    }
}