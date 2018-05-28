package com.iitu.trainapp.Utils;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class PositionAxisFormatter implements IAxisValueFormatter {
    private DecimalFormat mFormat;

    public PositionAxisFormatter() {
        mFormat = new DecimalFormat("###");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat.format(value) + " m";
    }
}
