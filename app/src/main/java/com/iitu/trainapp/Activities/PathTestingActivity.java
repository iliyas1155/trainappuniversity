package com.iitu.trainapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.iitu.trainapp.Cards.Path;
import com.iitu.trainapp.Cards.Vagon;
import com.iitu.trainapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.iitu.trainapp.Activities.PathsActivity.getChosenPathPosition;
import static com.iitu.trainapp.Activities.PathsActivity.getPath;
import static com.iitu.trainapp.Calculations.ListDataCalculator.calculatePressureInexes;

public class PathTestingActivity extends BaseActivity {
    LineChart chart;
    TextView pathName;
    TextView pathDesc;
    EditText massEditText;
    EditText resCoeffEditText;
    Button startPathTestButton;
    Path testingPath;
    List<Vagon> vagons;
    int testingPathPosition;
    double mass = 0;
    double resCoeff = 0;
    private FirebaseFirestore db;
    private static final String TAG = "PathTestingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_testing);
        db = FirebaseFirestore.getInstance();

        testingPathPosition = getChosenPathPosition();
        testingPath = getPath(testingPathPosition);

        chart = findViewById(R.id.path_test_chart);
        pathName = findViewById(R.id.testing_path_name);
        pathDesc = findViewById(R.id.testing_description);
        massEditText = findViewById(R.id.path_test_mass_edit_text);
        resCoeffEditText = findViewById(R.id.path_test_res_coeff_edit_text);
        startPathTestButton = findViewById(R.id.path_test_start_button);

        chart.setVisibility(View.INVISIBLE);

        getDbPathVerticalData(testingPath);

//        vagons = getIntent().getParcelableArrayListExtra("vagons");
//        if(vagons == null){
//            Log.d("fuck", "vagons == null");
//        }
    }

    private void setActivityData(){
        pathName.setText(testingPath.name);
        pathDesc.setText(getString(R.string.path_testing_message, testingPath.vertical_data.size()));
        startPathTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (massEditText.getText().toString().isEmpty()
                        || resCoeffEditText.getText().toString().isEmpty()) {
                    Toast.makeText(PathTestingActivity.this, R.string.path_testing_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                mass = Double.parseDouble(massEditText.getText().toString());
                resCoeff = Double.parseDouble(resCoeffEditText.getText().toString());
                refreshChartData();
                chart.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refreshChartData() {

        Log.d("PathTestingActivity","setNumericalData() start");
        ArrayList<Double> pathVerticalData = testingPath.vertical_data;
        Log.d("PathTestingActivity","setNumericalData() pathVerticalData = " + pathVerticalData.size());
        ArrayList<Double> pressureIndexes = calculatePressureInexes(mass, resCoeff, pathVerticalData);
        Log.d("PathTestingActivity","setNumericalData() pressureIndexes = " + pressureIndexes.size());
        float pos = 0f;
        List<Entry> entriesPath = new ArrayList();
        List<Entry> entriesVagon = new ArrayList();
        for(int i = 0; i<pressureIndexes.size(); i++){
            entriesPath.add(new Entry(pos, pathVerticalData.get(i).floatValue()));
            entriesVagon.add(new Entry(pos, pressureIndexes.get(i).floatValue()));
            pos++;
        }
        LineDataSet linePathDataSet = new LineDataSet(entriesPath, getString(R.string.path));
        LineDataSet lineVagonDataSet = new LineDataSet(entriesVagon, getString(R.string.vagon));

        linePathDataSet.setColor(ColorTemplate.JOYFUL_COLORS[1]);
        linePathDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        linePathDataSet.setDrawCircles(false);
        linePathDataSet.setDrawCircleHole(false);
        linePathDataSet.setDrawFilled(true);
        linePathDataSet.setFillColor(ColorTemplate.JOYFUL_COLORS[1]);
        linePathDataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -1e35f; // Just min enough value.
            }
        });

        lineVagonDataSet.setColor(ColorTemplate.LIBERTY_COLORS[1]);
        lineVagonDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineVagonDataSet.setDrawCircles(false);
        lineVagonDataSet.setDrawCircleHole(false);

        LineData lineData = new LineData();
        lineData.addDataSet(lineVagonDataSet);
        lineData.addDataSet(linePathDataSet);

        chart.setBackground(getDrawable(R.drawable.white_rounded_shape));
        chart.setExtraOffsets(8, 16, 8, 16);
        chart.getDescription().setEnabled(false);
        chart.animateX(3000);
        chart.setData(lineData);
        chart.invalidate();

        Log.d("PathTestingActivity","setNumericalData() end ");
    }

    private void getDbPathVerticalData(final Path path){
        final String pathId = path.path_id;
        showProgressDialog();
        DocumentReference docRef = db.collection("paths_vertical_data").document(pathId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    hideProgressDialog();
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        path.setDataFromDb((ArrayList<Double>) document.getData().get("vertical_data"));
                        setActivityData();
                        Log.d(TAG, "\tDocument exists\tvertical data=" + path.vertical_data.size());
                    } else {
                        Log.d(TAG, "\tNo such document");
                    }
                } else {
                    Log.d(TAG, "\tget failed with ", task.getException());
                }
            }
        });
        Log.d(TAG, "\tgetDbPathVerticalData: " + pathId +"\tend");
    }
}
