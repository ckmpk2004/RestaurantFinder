package com.example.restaurantfinder;

import java.util.Locale;

public class LocationNameChanger {
    private String newName;


    public LocationNameChanger() {
    }

    //Change GPS returned location to valid FireBase directory path
    public String chName(String address) {

        if (Locale.getDefault().getLanguage() == "en") {//Check System Language

            if (address.contains("Tseung")) {
                newName = "TseungKwanO";
            } else if (address.contains("Hang Hau")) {
                newName = "HangHau";
            } else if (address.contains("Sha Tin")) {
                newName = "ShaTin";
            } else {
                newName = "HangHau";
            }//default, prevent error

        } else if (Locale.getDefault().getLanguage() == "zh") {
            if (address.contains("將軍澳")) {
                newName = "TseungKwanO_ZH";
            } else if (address.contains("坑口")) {
                newName = "HangHau_ZH";
            } else if (address.contains("沙田")) {
                newName = "ShaTin_ZH";
            } else {
                newName = "HangHau_ZH";
            }//default, prevent error
        }
        return newName;
    }

}
