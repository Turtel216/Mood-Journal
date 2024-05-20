package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Entry;
import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;
import com.dimitrios_papakonstantinou.mood_journal.exceptions.MoodCantGetAverage;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//TODO Add tests for class
//TODO Improve null safety
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
    public static Mood moodMode(List<Entry> entries) {
        //Convert List of entries to List of Moods
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

    public static Mood moodMean(List<Entry> entries) {
        // Convert List of entries to list of moods, with each mood given a specific number(int)
        var moods = entries.stream().map(Entry::moodToInt).toList();

        // Calculate average and return result
        var average = (float) sumMoods(moods) / (float)moods.size();

        //convert the float value to a mood enum
        return numberToMood(average);
    }

    public static Mood moodMedian(List<Entry> entries) {
        // Convert List of entries to list of moods, with each mood given a specific number(int)
        var moods = entries.stream().map(Entry::moodToInt).toList();

        // Sort the list
        moods = moods.stream()
                .sorted()
                .toList();

        // If the size of the list is odd it returns the middle value
        if(moods.size() % 2 != 0) {
            var middle = moods.get(
                    moods.size() / 2
            );

            return numberToMood(middle);
        }

        // if the mood is even we return the average of the 2 middle values
        var middleEntries = List.of( //get the 2 middle values
                moods.get(moods.size() / 2),
                moods.get(moods.size() / 2 - 1)
        );

        //calculate the average of the 2 middle values
        var average = (float) sumMoods(moods) / (float)moods.size();

        //convert the float value to a mood enum
        return numberToMood(average);
    }

    public static int sumMoods(List<Integer> moods) {
        // traverse the list and add each mood(int) to the sum
        AtomicInteger sum = new AtomicInteger();
        moods.forEach(sum::addAndGet);

        return sum.get();
    }

    //numberToMood is overloaded to be used for both float and int values
    // Maps a float value to a Mood enum
    public static Mood numberToMood(float mood) {
        if(mood >= 4.5)
            return Mood.GREAT;
        if(mood >= 3.5)
            return Mood.GOOD;
        if(mood >= 2.5)
            return Mood.MEH;
        if(mood >= 1.5)
            return Mood.BAD;

        return Mood.HORRIBLE;
    }

    //Maps an int value to a Mood enum
    public static Mood numberToMood(int mood) {
        if(mood == 5)
            return Mood.GREAT;
        if(mood == 4)
            return Mood.GOOD;
        if(mood == 3)
            return Mood.MEH;
        if(mood == 2)
            return Mood.BAD;

        return Mood.HORRIBLE;
    }
}
