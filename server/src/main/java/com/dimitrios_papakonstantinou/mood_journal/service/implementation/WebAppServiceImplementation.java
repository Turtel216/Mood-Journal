package com.dimitrios_papakonstantinou.mood_journal.service.implementation;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.EntryRepository;
import com.dimitrios_papakonstantinou.mood_journal.datasource.repositories.UserRepository;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryCouldNotBeSavedException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.EntryIdAndEntryDateNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.UserIdNotFoundException;
import com.dimitrios_papakonstantinou.mood_journal.service.WebAppService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.dimitrios_papakonstantinou.mood_journal.service.util.EntryAnalyzer.*;

@Service
@AllArgsConstructor
public class WebAppServiceImplementation implements WebAppService {

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveEntry(Entry entry) {
        if(userRepository.findById(entry.getUserId()).isPresent()) {
            try {
                entryRepository.save(entry);
                return "Entry saved successfully";
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

    @Override
    public List<Mood> getMood(Long userId) {
        if(entryRepository.findByUserId(userId).isPresent())
            return entryRepository.findByUserId(userId).get()
                    .stream().map(Entry::getMood)
                    .collect(Collectors.toList());

        throw new UserIdNotFoundException("Provided user id does not match any mood entry in the data base");
    }

    //TODO add proper error handling when calling the moodMean method
    @Override
    public Mood getMean(Long userId) {
        if(entryRepository.findByUserId(userId).isEmpty())
            throw new UserIdNotFoundException("Provided user id does not match any user in the data base");

        var entries = entryRepository.findByUserId(userId).get();

        return moodMean(entries);
    }

    //TODO add proper error handling when calling the moodMode method
    @Override
    public Mood getMode(Long userId) {
        if(entryRepository.findByUserId(userId).isEmpty())
            throw new UserIdNotFoundException("Provided user id does not match any user in the data base");

        var entries = entryRepository.findByUserId(userId).get();

        return moodMode(entries);
    }

    //TODO add proper error handling when calling the moodMedian method
    @Override
    public Mood getMedian(Long userId) {
        if(entryRepository.findByUserId(userId).isEmpty())
            throw new UserIdNotFoundException("Provided user id does not match any user in the data base");

        var entries = entryRepository.findByUserId(userId).get();

        return moodMedian(entries);
    }
}