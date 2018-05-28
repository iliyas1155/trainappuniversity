package com.iitu.trainapp.Utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


public class SpeedAxisFormatter implements IAxisValueFormatter{
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return value + " m/s";
    }
}
