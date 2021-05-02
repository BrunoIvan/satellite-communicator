package com.satellite.messenger.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class MessageUtils {

    private static void addIfPossible(Set<String> words, final List<String> aWords, final int i) {
        if (aWords.size() > i) {
            words.add(aWords.get(i));
        }
    }

    public static String getMessage(final List<String> aWords, final List<String> bWords, final List<String> cWords) {
        final List<List<String>> wordsSets = Stream.of(aWords, bWords, cWords)
                .sorted((i, j) -> i.size() > j.size() ? -1 : 0)
                .collect(Collectors.toList());
        final Set<String> words = new LinkedHashSet<>();
        range(0, wordsSets.get(0).size())
                .forEach(i -> {
                    addIfPossible(words, wordsSets.get(0), i);
                    addIfPossible(words, wordsSets.get(1), i);
                    addIfPossible(words, wordsSets.get(2), i);
                });
        words.remove("");
        return String.join(" ", words);
    }

}
