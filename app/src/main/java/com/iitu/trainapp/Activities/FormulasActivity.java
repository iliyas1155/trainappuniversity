package com.iitu.trainapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.iitu.trainapp.R;

import org.w3c.dom.Text;

public class FormulasActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas);

        TextView textLine1 = findViewById(R.id.line1_text);
        TextView textLine2 = findViewById(R.id.line2_text);
        TextView textLine3 = findViewById(R.id.line3_text);

        textLine1.setText(R.string.formula_description_1);
        textLine2.setText(R.string.formula_description_2);
        textLine3.setText(R.string.formula_description_3);

    }
}
