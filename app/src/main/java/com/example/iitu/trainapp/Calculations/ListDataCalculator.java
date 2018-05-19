package com.example.iitu.trainapp.Calculations;
/**
 * ListDataCalculator calculates whole path changing indexes
 * and puts them into ArrayList of data
 */

import java.util.ArrayList;


public class ListDataCalculator {
    public static ArrayList<Double> calculatePressureInexes(double mass, double c, ArrayList<Double> pathVerticalData){
        ArrayList<Double> pressureIndexes = new ArrayList();
        for(double vertical : pathVerticalData){
            pressureIndexes.add(DataCalculator.calcZ(vertical,mass,c));
        }
        return pressureIndexes;
    }
}
