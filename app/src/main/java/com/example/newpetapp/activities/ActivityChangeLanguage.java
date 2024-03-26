package com.example.newpetapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newpetapp.R;

import java.util.Locale;

public class ActivityChangeLanguage extends AppCompatActivity {
    ImageView ivBack;
    Spinner selectLanguage;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        ivBack = findViewById(R.id.ivBackArrow);
        selectLanguage = findViewById(R.id.spinner);
        btnSave = findViewById(R.id.btnSave);

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Load saved language from SharedPreferences, default to English
        String savedLanguage = sharedPreferences.getString("language", "en");

        String[] languages = {"English", "Russian", "Estonian"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                languages
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        selectLanguage.setAdapter(adapter);

        // Set saved language as selected in the spinner
        int savedLanguagePosition = adapter.getPosition(savedLanguage);
        selectLanguage.setSelection(savedLanguagePosition);

        // Set listener for spinner item selection
        selectLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLanguage = parentView.getItemAtPosition(position).toString();
                // Save selected language in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("language", getLanguageCode(selectedLanguage));
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Apply selected language
                applyLanguage();

               Intent intent=new Intent(ActivityChangeLanguage.this,ActivityHome.class);
               startActivity(intent);
               finish();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en";
            case "Russian":
                return "ru";
            case "Estonian":
                return "et";
            default:
                return "en"; // Default to English
        }
    }

   public void applyLanguage() {
        // Retrieve saved language from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ActivityChangeLanguage.this);
        String language = sharedPreferences.getString("language", "en");
        // Create Locale object based on selected language
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }

}
