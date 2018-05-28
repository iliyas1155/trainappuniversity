package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iitu.trainapp.Cards.Vagon;
import com.iitu.trainapp.R;

import java.util.List;

public class SequensesMenuActivity extends BaseActivity {
    Button randomSequenseB, customSequenseB;
    public static List<Vagon> vagons = null;
    TextView introTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequenses_menu);

        randomSequenseB = findViewById(R.id.random_sequense_button);
        customSequenseB = findViewById(R.id.cusrom_sequense_button);
        introTextView = findViewById(R.id.sequenses_introduction_text);

        introTextView.setText(R.string.sequences_intro);
        setButtonsClickable();
    }
    private void setButtonsClickable(){
        customSequenseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SequensesMenuActivity.this, SequensesCustomActivity.class);
                SequensesMenuActivity.this.startActivity(myIntent);
            }
        });
        randomSequenseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SequensesMenuActivity.this, SequensesRandomActivity.class);
                SequensesMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
