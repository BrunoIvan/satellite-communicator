package com.satellite.messenger.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.collections.Lists.newArrayList;

@Test
public class MessageUtilsTest {

    public void getMessageOk() {
        final List<String> aWords = newArrayList("", "Este", "es", "", "mensaje");
        final List<String> bWords = newArrayList("Este", "", "un", "mensaje");
        final List<String> cWords = newArrayList("", "Este", "", "", "mensaje");
        Assert.assertEquals(MessageUtils.getMessage(aWords, bWords, cWords), "Este es un mensaje");
    }

}
