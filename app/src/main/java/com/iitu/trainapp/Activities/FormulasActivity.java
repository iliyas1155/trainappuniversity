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
        TextView textLine4 = findViewById(R.id.line4_text);
        TextView textLine5 = findViewById(R.id.line5_text);
        TextView textLine6 = findViewById(R.id.line6_text);
        TextView textLine7 = findViewById(R.id.line7_text);

        textLine1.setText(R.string.formula_description_1);
        textLine2.setText(R.string.formula_description_2);
        textLine3.setText(R.string.formula_description_3);
        textLine4.setText(R.string.formula_description_4);
        textLine5.setText(R.string.formula_description_5);
        textLine6.setText(R.string.formula_description_6);
        textLine7.setText(R.string.formula_description_7);

    }
}
