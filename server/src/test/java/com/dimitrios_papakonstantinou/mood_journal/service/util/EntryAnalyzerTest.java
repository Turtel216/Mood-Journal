package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.MoodCantGetAverage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.dimitrios_papakonstantinou.mood_journal.service.util.EntryAnalyzer.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class EntryAnalyzerTest {

    List<Entry> entries;
    Entry entry1;
    Entry entry2;
    Entry entry3;
    Entry entry4;
    Entry entry5;
    Entry entry6;
    Map<Mood, Integer> counter;

    @BeforeEach
    void setUp() {
        entry1 = new Entry(1L, 1L, "some text", Mood.GOOD, "05.05.2024");
        entry2 = new Entry(1L, 1L, "some text", Mood.BAD, "05.05.2024");
        entry3 = new Entry(1L, 1L, "some text", Mood.HORRIBLE, "05.05.2024");
        entry4 = new Entry(1L, 1L, "some text", Mood.GREAT, "05.05.2024");
        entry5 = new Entry(1L, 1L, "some text", Mood.MEH, "05.05.2024");
        entry6 = new Entry(1L, 1L, "some text", Mood.BAD, "05.05.2024");

        entries = List.of(entry1,  entry2, entry3, entry4, entry5, entry6);

        counter = new HashMap<>();
        counter.put(Mood.GREAT, 1);
        counter.put(Mood.BAD, 2);
        counter.put(Mood.HORRIBLE, 1);
        counter.put(Mood.GOOD, 1);
        counter.put(Mood.MEH, 1);
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

        counter = null;
    }

    // Test case Success
    @Test
    void testCountMoods_Success() {
        var moods = entries.stream().map(Entry::getMood).toList();
        assertThat(countMoods(moods).equals(counter)).isTrue();
    }

    // Test case Failure
    @Test
    void testCountMoods_Failure() {

        var falseCounter = new HashMap<>();
        falseCounter.put(Mood.GREAT, 3);
        falseCounter.put(Mood.BAD, 1);
        falseCounter.put(Mood.HORRIBLE, 1);
        falseCounter.put(Mood.GOOD, 3);
        falseCounter.put(Mood.MEH, 1);

        var moods = entries.stream().map(Entry::getMood).toList();
        assertThat(countMoods(moods).equals(falseCounter)).isFalse();
    }


    // Test case Failure
    @Test
    void moodMode_Success() {
        assertThat(moodMode(entries).equals(Mood.BAD)).isTrue();
    }
}