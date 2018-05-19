package com.example.iitu.trainapp.Calculations;
/**
 * DataCalculator contains basic formulas to
 * calculate physical parameters
 */

import java.util.ArrayList;

public class DataCalculator {
    public static final double constPuasson = 0.3;

    public static double calcC(double e, double d, double D, double N){
        final double G = e / (2*(1+ constPuasson));
        final double C = G * Math.pow(d,4) / (8 * Math.pow(D,3) * N);
        return C;
    }

    public static double calcSequentialC(ArrayList<Double> cArray){
        double seqC = 0;
        for(double c : cArray){
            seqC += (1 / c);
        }
        seqC = 1 / seqC;
        return seqC;
    }

    public static double calcParallelC(ArrayList<Double> cArray){
        double parallelC = 0;
        for(double c : cArray){
            parallelC += c;
        }
        return parallelC;
    }

    public static double calcCircularFrequency(double c, double m){
        double circFreq = Math.sqrt(c/m);
        return circFreq;
    }

    public static double calcLinearFrequency(double c, double m){
        double circFreq = Math.sqrt(c/m);
        double linearFreq = circFreq / (2 * 3.14);
        return linearFreq;
    }

    public static double calcLinearFrequency(double circFreq){
        double linearFreq = circFreq / (2 * 3.14);
        return linearFreq;
    }

    /*
    *         h*sqrt(m*c)
    * beta = -------------
    *              z
     */
    public static double calcResistanceCoeff(double h, double m, double c, double z){
        double beta = h * Math.sqrt(m*c) / z;
        return beta;
    }

    /*
    *
    * zк = h * cos(w*t)
    *
     */
//    public static double calcZk(double h, double w, double t){
//        double Zk = h * Math.cos();
//        return Zk;
//    }

    /*потребный коэфф отн трения фркционных клиновых гасителей
    *
    * fi = 3.16 * h / (4 * fст)
    * fст - статич прогиб рессоровых комплектов[м]
    * fi - потребный коэффициент отн. трения фрикционных клиновых гасителей
     */
    public static double calcNessCoeffOfRelativeFriction(double h, double m, double c){
        double fst = m * 9.8 / c;
        double fi = 3.16 * h / (4 * fst);
        return fi;
    }

    public static double calcZ(double h, double m, double c){
        double z = m*c + h;
        return z;
    }
}
