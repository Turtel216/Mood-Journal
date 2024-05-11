package com.dimitrios_papakonstantinou.mood_journal.datasource.repositories;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class EntryRepositoryTest {

    @Autowired
    private EntryRepository entryRepository;
    Entry entry;

    @BeforeEach
    void setUp() {
        Mood mood = Mood.GOOD;
        entry = new Entry(1L, 1L, "some text", mood, "05.05.2024");
        entryRepository.save(entry);
    }

    @AfterEach
    void tearDown() {
        entry = null;
        entryRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    void testFindByEntryDateAndUserId_Found() {
        Optional<Entry> foundEntry = entryRepository.findByEntryDateAndUserId("05.05.2024", 1L);

        assertThat(foundEntry.get().getId().equals(entry.getId())).isTrue();
        assertThat(foundEntry.get().getEntryDate().equals(entry.getEntryDate())).isTrue();
        assertThat(foundEntry.get().getUserId().equals(entry.getUserId())).isTrue();
        assertThat(foundEntry.get().getText().equals(entry.getText())).isTrue();
        assertThat(foundEntry.get().getMood().equals(entry.getMood())).isTrue();

    }

    // Test case FAILURE
    @Test
    void testFindByEntryDatedUserId_NotFound() {

        Optional<Entry> entry1 = entryRepository.findByEntryDateAndUserId("06.05.2024", 1L);
        Optional<Entry> entry2 = entryRepository.findByEntryDateAndUserId("05.05.2024", 4L);
        Optional<Entry> entry3 = entryRepository.findByEntryDateAndUserId("06.05.2024", 2L);
        assertThat(entry1.isEmpty()).isTrue();
        assertThat(entry2.isEmpty()).isTrue();
        assertThat(entry3.isEmpty()).isTrue();
    }
}