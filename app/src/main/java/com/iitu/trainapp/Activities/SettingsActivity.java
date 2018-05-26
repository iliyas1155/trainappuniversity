package com.iitu.trainapp.Activities;

import android.os.Bundle;

import com.iitu.trainapp.R;
import com.iitu.trainapp.Utils.SharedPreferencesUtil;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener  {
    Spinner languagesSpinner;
    Button toAbout, changeLanguage;
    String[] languages = {"ru","en"};
    int chosenLanguage = 0;
    static boolean isLanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languagesSpinner = findViewById(R.id.languages_spinner);
        changeLanguage = findViewById(R.id.activate_changes_button);
        toAbout = findViewById(R.id.to_about_button);

        languagesSpinner.setOnItemSelectedListener(this);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, AboutActivity.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(languages[chosenLanguage]);
                isLanguageChanged = true;
                SharedPreferencesUtil.setLanguage(SettingsActivity.this, languages[chosenLanguage]);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosenLanguage = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("Printing","onNothingSelected()");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isLanguageChanged) {
            Intent refreshTask = new Intent(this, MainMenuActivity.class);
            refreshTask.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(refreshTask);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
