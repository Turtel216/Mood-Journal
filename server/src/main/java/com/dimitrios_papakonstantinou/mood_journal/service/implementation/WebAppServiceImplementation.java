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

    public String saveEntry(Entry entry) {
        if(userRepository.findByUserId(entry.getUserId()).isPresent()) {
            try {
                entryRepository.save(entry);
                return "Success";
            } catch(Exception e){
                //TODO throw proper exception
                return "Failure" + e.toString();
            }
        }
        return "Invalid user ID";
    }

    public Entry getEntry(Long userId) {
        var currentDate = "05.10.2024"; //TODO get real date
        if(entryRepository.findByUserId(userId).findByDate(currentDate).isPressent()) {
            return null;
        }

        return new Entry(); //TODO call static factory for entry not found
    }
}
