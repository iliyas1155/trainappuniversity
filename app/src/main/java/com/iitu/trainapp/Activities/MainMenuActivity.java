package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iitu.trainapp.R;

public class MainMenuActivity extends BaseActivity {
    Button pathsB, sequencesB, aboutB;
    TextView introT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        pathsB = findViewById(R.id.paths_button);
        sequencesB = findViewById(R.id.sequenses_button);
        aboutB = findViewById(R.id.about_button);
        introT = findViewById(R.id.introduction_text_view);

        introT.setText("This app gives basic introduction into trains mechanic.\n" +
                        "Bellow you can see buttons: paths, sequenses and about.\n" +
                        "Paths button opens menu of paths.\n" +
                        "Sequenses button opens menu of sequenses of vagons.\n" +
                        "About button opens detailed description of current app.");
        setButtonsClickable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBarIcon();
    }

    private void setButtonsClickable(){
        pathsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, PathsActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });
        sequencesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, SequensesMenuActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });
        aboutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, AboutActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
