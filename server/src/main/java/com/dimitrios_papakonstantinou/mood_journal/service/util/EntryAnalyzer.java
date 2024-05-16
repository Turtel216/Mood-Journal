package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Add tests for class
public class EntryAnalyzer {

    public static Map<Mood, Integer> countMoods(List<Mood> moods) {

        Map<Mood, Integer> counter = new HashMap<>();

        moods.forEach(mood -> {
            switch (mood) {
                case GREATE -> counter.put(Mood.GREATE, counter.get(Mood.GREATE) + 1);
                case GOOD -> counter.put(Mood.GOOD, counter.get(Mood.GOOD) + 1);
                case BAD -> counter.put(Mood.BAD, counter.get(Mood.BAD) + 1);
                case HORRIBLE -> counter.put(Mood.BAD, counter.get(Mood.HORRIBLE) + 1);
            }
        });
        return counter;
    }

    public static Mood averageMood(List<Entry> entries) {
        //Convert List of entries to Array of Moods
        var moods = entries.stream().map(Entry::getMood).toList();

        // count of each mood
        Map<Mood, Integer> moodCount = countMoods(moods);

        // return the mood with the highest number of entries. Return null if there is no max
        return moodCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

    }
}
