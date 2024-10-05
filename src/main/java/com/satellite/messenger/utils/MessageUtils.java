package com.satellite.messenger.utils;

import com.satellite.messenger.utils.exceptions.message.MessageException;
import com.satellite.messenger.utils.exceptions.message.NoMessageFoundException;

import java.util.*;

public class MessageUtils {

    protected MessageUtils() {
    }

    public static String getMessage(
            final List<String> aWords,
            final List<String> bWords,
            final List<String> cWords
    ) {
        final List<List<String>> wordsSets = new ArrayList<>(Arrays.asList(aWords, bWords, cWords));
        wordsSets.sort((i, j) -> i.size() < j.size() ? -1 : 0);
        final List<String> result = new ArrayList<>();
        int bound = wordsSets.get(0).size();
        for (int index = 0; index < bound; index++) {
            Set<String> words = getWordsR(0, index, wordsSets, new LinkedHashSet<>());
            if (words.size() == 1) {
                result.addAll(words);
            } else {
                throw new NoMessageFoundException();
            }
        }
        return String.join(" ", result);
    }

    private static Set<String> getWordsR(final int setIndex, final int wordIndex, final List<List<String>> wordsSets, final LinkedHashSet<String> result) {
        if (wordsSets.size() == setIndex) {
            return result;
        }

        final int difference = wordsSets.get(setIndex).size() - wordsSets.get(0).size();
        final String word = wordsSets.get(setIndex).get(wordIndex + difference);
        if (!word.isEmpty()) {
            result.add(word);
        }

        return getWordsR(setIndex + 1, wordIndex, wordsSets, result);
    }

}
