package com.dimitrios_papakonstantinou.mood_journal.service;


import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;

import java.util.List;

public interface WebAppService {
    public String saveEntry(Entry entry);
    public Entry getEntry(Long userId, String currentDate);
    public User getUser(Long userId);
    public List<Mood> getMood(Long userId);
    public Mood getMean(Long userId);
}
