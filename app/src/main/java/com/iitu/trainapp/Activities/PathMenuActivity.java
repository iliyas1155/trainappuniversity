package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iitu.trainapp.R;


public class PathMenuActivity extends BaseActivity {
    TextView pathNameTextView;
    Button pathInfoButton;
    Button formulasButton;
    Button pathTestButton;
    Button whatWeDoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_menu);

        pathInfoButton = findViewById(R.id.path_info_button);
        formulasButton = findViewById(R.id.formulas_button);
        pathTestButton = findViewById(R.id.path_test_button);
        whatWeDoButton = findViewById(R.id.what_we_do_button);
        pathNameTextView = findViewById(R.id.path_name_in_menu);

        pathNameTextView.setText(PathsActivity.getPath(PathsActivity.getChosenPathPosition()).name);
        pathTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PathMenuActivity.this, PathTestingActivity.class);
                PathMenuActivity.this.startActivity(myIntent);
            }
        });
        formulasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PathMenuActivity.this, FormulasActivity.class);
                PathMenuActivity.this.startActivity(myIntent);
            }
        });
        whatWeDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PathMenuActivity.this, FormulasActivity.class);
                PathMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
