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

        textLine1.setText("Oscillations that occur under the influence of an external periodic force are called forced oscillations.");
        textLine2.setText("In this case, the external force performs positive work and ensures the influx of energy to the oscillatory system. It does not allow oscillations to decay, despite the action of frictional forces.");
        textLine3.setText("If the frequency of the external force approaches the natural frequency, there is a sharp increase in the amplitude of the forced oscillations. This phenomenon is called resonance.");

    }
}
