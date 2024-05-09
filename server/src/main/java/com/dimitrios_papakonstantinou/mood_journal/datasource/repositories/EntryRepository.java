package com.dimitrios_papakonstantinou.mood_journal.datasource.repositories;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    //TODO add find by date

    //TODO add find by UserId
}
