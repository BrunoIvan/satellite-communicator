package com.satellite.messenger.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class MessageUtils {

    public static String getMessage(final List<String> aWords, final List<String> bWords, final List<String> cWords) {
        final List<List<String>> wordsSets = Stream.of(aWords, bWords, cWords)
                .sorted((i, j) -> i.size() < j.size() ? -1 : 0)
                .collect(Collectors.toList());
        final List<String> result = new ArrayList<>();
        range(0, wordsSets.get(0).size())
                .forEach(index -> {
                    final Set<String> words = getWordsR(0, index, wordsSets, new LinkedHashSet<>());
                    if (words.size() == 1) {
                        result.addAll(words);
                    } else {
                        throw new IllegalArgumentException("Could not determine message");
                    }
                });
        return String.join(" ", result);
    }

    private static Set<String> getWordsR(final int setIndex, final int wordIndex, final List<List<String>> wordsSets, final LinkedHashSet<String> result) {
        if (wordsSets.size() == setIndex) {
            return result;
        }

        final int difference = wordsSets.get(setIndex).size() - wordsSets.get(0).size();
        final String word = wordsSets.get(setIndex).get(wordIndex + difference);
        if (!word.equals("")) {
            result.add(word);
        }

        return getWordsR(setIndex + 1, wordIndex, wordsSets, result);
    }

}
