package com.example.newpetapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LanguageManager {

    public static void applyLanguage(Context context) {
        // Retrieve saved language from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPreferences.getString("language", "en");
        // Create Locale object based on selected language
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}

