package com.iitu.trainapp.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.iitu.trainapp.Calculations.DataCalculator;
import com.iitu.trainapp.Cards.Vagon;
import com.iitu.trainapp.Cards.VagonsCardsAdapterNotSettable;
import com.iitu.trainapp.R;
import com.iitu.trainapp.Utils.PositionAxisFormatter;
import com.iitu.trainapp.Utils.SpeedAxisFormatter;
import com.iitu.trainapp.Utils.TimeAxisFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SequenceTestAcivity extends BaseActivity {
    LineChart chart;
    EditText speedEditText;
    Button testResonanceButton;
    Button testStopButton;
    RecyclerView vagonsRv;
    List<Vagon> vagons;//todo use uneditable adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_test);

        testResonanceButton = findViewById(R.id.test_resonance_button);
        testStopButton = findViewById(R.id.test_stop_button);
        speedEditText = findViewById(R.id.speed_edit_text);
        chart = findViewById(R.id.speed_chart);
        chart.setVisibility(View.GONE);

        vagonsRv = findViewById(R.id.rv_vagons);

        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vagonsRv.setLayoutManager(llm1);

        vagons = SequensesMenuActivity.vagons;

        VagonsCardsAdapterNotSettable adapter = new VagonsCardsAdapterNotSettable(vagons, true);
        vagonsRv.setAdapter(adapter);

        testStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speedStr = speedEditText.getText().toString();
                if(speedStr != null && !speedStr.isEmpty()){
                    chart.setVisibility(View.VISIBLE);
                    double initialSpeed = Double.parseDouble(speedStr);
                    timeChartInit(initialSpeed);
                }
            }
        });

        testResonanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chart.setVisibility(View.VISIBLE);
                TreeMap<Double, Integer> resSpeedMap = findResSpeeds();
                resonanceChartInit(resSpeedMap);
            }
        });

    }

    public TreeMap<Double, Integer> findResSpeeds(){
        TreeMap<Double, Integer> resonances = new TreeMap();
        for(Vagon vagon : vagons){
            double circFreq = DataCalculator.calcCircularFrequency(8000, vagon.mass);//каждого вагона
            double resonanceSpeed = DataCalculator.calcResonanceSpeed(circFreq);//каждого вагона
            Log.d("SequencesTest", "resonanceSpeed => " + resonanceSpeed + "mass => " + vagon.mass);

            int amount = 1;
            if(resonances.containsKey(resonanceSpeed)){
                amount = resonances.get(resonanceSpeed);
            }
            resonances.put(resonanceSpeed, amount);
        }
        return resonances;

    }

    private void timeChartInit(double initialSpeed) {
        double maxMass = 0;
        double sumMass = 0, avgMass;
        for(Vagon vagon : vagons){
            double mass = vagon.mass;
            if(mass > maxMass){
                maxMass = mass;
            }
            sumMass += mass;
        }
        avgMass = sumMass/vagons.size();

        List<Double> stopTimes = DataCalculator.calcStopTime(initialSpeed, maxMass, avgMass);

        float worstStopTime = Float.parseFloat(stopTimes.get(0) + "");
        float bestStopTime = Float.parseFloat(stopTimes.get(1) + "");
        float initSpeed = Float.parseFloat(initialSpeed + "");

        List<Entry> entriesSpeedWorst = speedChangeOnStop(initSpeed, worstStopTime);
        List<Entry> entriesSpeedBest = speedChangeOnStop(initSpeed, bestStopTime);

        LineDataSet worstDataSet = new LineDataSet(entriesSpeedWorst, "Speed (worst case)");//todo create editing line
        LineDataSet bestDataSet = new LineDataSet(entriesSpeedBest, "Speed (best case)");

        bestDataSet.setColor(ColorTemplate.MATERIAL_COLORS[3]);
        bestDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        bestDataSet.setDrawCircles(false);
        bestDataSet.setDrawCircleHole(false);

        worstDataSet.setColor(ColorTemplate.MATERIAL_COLORS[2]);
        worstDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        worstDataSet.setDrawCircles(false);
        worstDataSet.setDrawCircleHole(false);

        LineData lineData = new LineData();
        lineData.addDataSet(bestDataSet);
        lineData.addDataSet(worstDataSet);

        chart.setBackground(getDrawable(R.drawable.white_rounded_shape));
        chart.setExtraOffsets(8, 16, 8, 16);
        chart.getDescription().setEnabled(false);
        chart.animateX(3000);
        chart.getAxisLeft().setValueFormatter(new SpeedAxisFormatter());
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setValueFormatter(new TimeAxisFormatter());
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        chart.setData(lineData);
        chart.invalidate();

        Log.d("SequenceTestActivity","setNumericalData() end ");
    }

    private List<Entry> speedChangeOnStop(float initSpeed, float time) {

        float mostStopping = 0;

        float initStopping = -(initSpeed/(time*0.2f));//замедление в начале
        float initStoppingChange = (initStopping/(time*0.9f));//снижение замедления в начале

        float speed = initSpeed;
        float initStoppingAcc = 0;

        List<Entry> entriesSpeed = new ArrayList();
        for(float i = 0; i < time; i+=0.1f){
            if(i<time*0.2f){
                initStoppingAcc = initStoppingAcc + initStoppingChange * 0.1f;

                speed = speed + initStoppingAcc * 0.1f;
                Log.d("speedChange", "i = " + i + "\tinitStoppingAcc = " + initStoppingAcc + "\tspeed = " + speed);

                mostStopping = -(speed/(time*0.8f));
            }else{
                speed = speed + mostStopping * 0.1f;
            }
            entriesSpeed.add(new Entry(i, speed));
        }

        return entriesSpeed;
    }

    private void resonanceChartInit(TreeMap<Double, Integer> resonanceMap) {

        float pos = 0f;
        final float initialResonanceSpeed = Float.parseFloat(resonanceMap.firstKey() + "");
        final float finalResonanceSpeed = Float.parseFloat(resonanceMap.lastKey() + "");
        List<Entry> entriesLine = new ArrayList();
        List<Entry> entriesResonance = new ArrayList();
        while(pos < finalResonanceSpeed + 100f){
            entriesLine.add(new Entry(pos, pos));
            entriesResonance.add(new Entry(pos, initialResonanceSpeed));
            pos++;
        }
        LineDataSet lineSpeedDataSet = new LineDataSet(entriesLine, "Speed");
        LineDataSet lineResonanceDataSet = new LineDataSet(entriesResonance, "Resonance risk");

        lineResonanceDataSet.setColor(ColorTemplate.MATERIAL_COLORS[2]);
        lineResonanceDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineResonanceDataSet.setDrawCircles(false);
        lineResonanceDataSet.setDrawCircleHole(false);
        lineResonanceDataSet.setDrawFilled(true);
        lineResonanceDataSet.setFillColor(ColorTemplate.MATERIAL_COLORS[2]);
        lineResonanceDataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return finalResonanceSpeed; // Just max enough value.
            }
        });

        lineSpeedDataSet.setColor(ColorTemplate.MATERIAL_COLORS[1]);
        lineSpeedDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineSpeedDataSet.setDrawCircles(false);
        lineSpeedDataSet.setDrawCircleHole(false);

        LineData lineData = new LineData();
        lineData.addDataSet(lineSpeedDataSet);
        lineData.addDataSet(lineResonanceDataSet);

        chart.setBackground(getDrawable(R.drawable.white_rounded_shape));
        chart.setExtraOffsets(8, 16, 8, 16);
        chart.getDescription().setEnabled(false);
        chart.animateX(3000);
        chart.getAxisLeft().setValueFormatter(new SpeedAxisFormatter());
        chart.getXAxis().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setData(lineData);
        chart.invalidate();


        Log.d("SequenceTestActivity","setNumericalData() end ");
    }
}
