package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.MoodCantGetAverage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Add tests for class
public class EntryAnalyzer {

    public static Map<Mood, Integer> countMoods(List<Mood> moods) {

        // Hash map for counting each mood kind
        Map<Mood, Integer> counter = new HashMap<>();

        // Initialize hash map
        counter.put(Mood.GREAT, 0);
        counter.put(Mood.GOOD, 0);
        counter.put(Mood.MEH, 0);
        counter.put(Mood.BAD, 0);
        counter.put(Mood.HORRIBLE, 0);

        // Count each mood kind
        moods.forEach(mood -> {
            switch (mood) {
                case GREAT -> counter.put(Mood.GREAT, counter.get(Mood.GREAT) + 1);
                case GOOD -> counter.put(Mood.GOOD, counter.get(Mood.GOOD) + 1);
                case MEH -> counter.put(Mood.MEH, counter.get(Mood.MEH) + 1);
                case BAD -> counter.put(Mood.BAD, counter.get(Mood.BAD) + 1);
                case HORRIBLE -> counter.put(Mood.HORRIBLE, counter.get(Mood.HORRIBLE) + 1);
            }
        });

        return counter;
    }

    // Throws Exception
    public static Mood MoodMode(List<Entry> entries) {
        //Convert List of entries to Array of Moods
        var moods = entries.stream().map(Entry::getMood).toList();

        // count of each mood
        Map<Mood, Integer> moodCount = countMoods(moods);

        // return the mood with the highest number of entries. Return null if there is no max
        var result = moodCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // Throw error its impossible to find the maximum
        if(result == null)
            throw new MoodCantGetAverage("Couldn't calculate mood average");

        return result;
    }
}
