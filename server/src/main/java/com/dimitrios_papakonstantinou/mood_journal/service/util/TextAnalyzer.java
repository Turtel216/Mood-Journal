package com.dimitrios_papakonstantinou.mood_journal.service.util;

import com.dimitrios_papakonstantinou.mood_journal.datasource.models.Mood;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TextAnalyzer {

    //List of tokens which should be filtered out of each text
    private static final HashSet<String> ignoredTokens = new HashSet<>
            (List.of("?" ,"!", ",", ":", ";", ".", "(", ")"));

    public static List<String> tokeniseText(String text) {
        // Create a tokens by splitting the text at between each space
        List<String> tokens = Arrays.stream(text.split(" ")).toList();

        // Filter out all tokens which are inside the ignoredTokens list and return the results
         return tokens.stream().dropWhile(ignoredTokens::contains).toList();
    }

    public static HashMap<Mood, String> mapMoodToToken(List<String> tokens, Mood mood) {
        // Create new hashMap and map each token to a mood
        HashMap<Mood, String> map = new HashMap<>();
        tokens.forEach(token -> map.put(mood, token));

        return map;
    }
}
