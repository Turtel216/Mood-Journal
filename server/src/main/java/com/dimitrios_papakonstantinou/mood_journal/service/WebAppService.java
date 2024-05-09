package com.dimitrios_papakonstantinou.mood_journal.service;


import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;

public interface WebAppService {
    public String saveEntry(Entry entry);
    public Entry getEntry(Long userId);
}
