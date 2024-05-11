package com.dimitrios_papakonstantinou.mood_journal.datasource.repositories;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByEntryDateAndUserId(String date, Long userId);
}
