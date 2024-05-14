package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryCouldNotBeSavedException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryIdAndEntryDateNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.UserIdNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebAppServiceImplementation implements WebAppService {

    private EntryRepository entryRepository;
    private UserRepository userRepository;

    @Override
    public String saveEntry(Entry entry) {
        if(userRepository.findById(entry.getUserId()).isPresent()) {
            try {
                entryRepository.save(entry);
                return "Success";
            } catch(Exception e){
                throw new EntryCouldNotBeSavedException("The entity could not be saved", e.getCause());
            }
        }

        throw new UserIdNotFoundException("Provided entity does not match any user");
    }

    @Override
    public Entry getEntry(Long userId, String currentDate) {
        if(entryRepository.findByEntryDateAndUserId(currentDate, userId).isPresent()) {
            return entryRepository.findByEntryDateAndUserId(currentDate, userId).get();
        }

        throw new EntryIdAndEntryDateNotFoundException("Request entry not found");
    }

    @Override
    public User getUser(Long userId) {
        if(userRepository.findById(userId).isPresent())
            return userRepository.findById(userId).get();

        throw new UserIdNotFoundException("Provided user id does not match any user in the data base");
    }
}