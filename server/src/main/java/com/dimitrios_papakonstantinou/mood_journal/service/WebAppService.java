package com.dimitrios_papakonstantinou.mood_journal.service;


import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.User;

public interface WebAppService {
    public String saveEntry(Entry entry);
    public Entry getEntry(Long userId, String currentDate);
    public User getUser(Long userId);
}
