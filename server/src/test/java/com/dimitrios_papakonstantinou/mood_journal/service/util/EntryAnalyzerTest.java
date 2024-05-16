package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dimitrios_papakonstantinou.mood_journal.service.util.EntryAnalyzer.countMoods;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntryAnalyzerTest {

    List<Entry> entries;
    Entry entry1;
    Entry entry2;
    Entry entry3;
    Entry entry4;
    Entry entry5;
    Entry entry6;

    @BeforeEach
    void setUp() {
        entry1 = new Entry(1L, 1L, "some text", Mood.GOOD, "05.05.2024");
        entry2 = new Entry(1L, 1L, "some text", Mood.BAD, "05.05.2024");
        entry3 = new Entry(1L, 1L, "some text", Mood.HORRIBLE, "05.05.2024");
        entry4 = new Entry(1L, 1L, "some text", Mood.GREATE, "05.05.2024");
        entry5 = new Entry(1L, 1L, "some text", Mood.BAD, "05.05.2024");
        entry6 = new Entry(1L, 1L, "some text", Mood.GOOD, "05.05.2024");

        entries = List.of(entry1, entry2, entry3, entry4, entry5, entry6);
    }

    @AfterEach
    void tearDown() {
        entry1 = null;
        entry2 = null;
        entry3 = null;
        entry4 = null;
        entry5 = null;
        entry6 = null;

        entries = null;
    }

    // Test case Success
    @Test
    void countMoods_Success() {

    }

    // Test case failure, return null
    @Test
    void countMoods_ReturnNull() {
    }

    @Test
    void averageMood() {
    }
}