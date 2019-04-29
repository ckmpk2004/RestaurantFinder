package com.example.restaurantfinder;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class LocationNameChangerTest {

    @Test
    public void chName() throws Exception {
        if (Locale.getDefault().getLanguage() == "en") {
            String input = "Hang Hau";
            String output;
            String expected = "HangHau";

            LocationNameChanger nameChanger = new LocationNameChanger();
            output = nameChanger.chName(input);

            assertEquals(expected, output);
        } else if (Locale.getDefault().getLanguage() == "zh") {
            String input = "坑口";
            String output;
            String expected = "HangHau_ZH";

            LocationNameChanger nameChanger = new LocationNameChanger();
            output = nameChanger.chName(input);

            assertEquals(expected, output);
        }
    }
}