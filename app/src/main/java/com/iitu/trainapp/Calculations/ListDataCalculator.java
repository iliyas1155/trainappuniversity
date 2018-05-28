package com.iitu.trainapp.Calculations;
/**
 * ListDataCalculator calculates whole path changing indexes
 * and puts them into ArrayList of data
 */

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;


public class ListDataCalculator {
    private static final double STAND_ELEVATION_OF_VAGON = 2d;
    public static List<Entry> calculateVagonPositions(double mass, double speed, ArrayList<Double> pathVerticalData){
//        ArrayList<Double> vagonPos = new ArrayList();
//        double sumLastElements = 0;
//        double limit = 1000;
//        double avg = 0;
//        List<Double> lastElements = new ArrayList();
//        double counter = 0;
        List<Entry> entriesVagon = new ArrayList();
        double upDist = 0;
        double fullDist = 0;

        double prevZDist = 0d;
        boolean up = false;
        mass = 1000*mass;
        double gravityForce = mass * 9.8;
        float xPos = 0;
        for(int i=1; i<pathVerticalData.size(); i++){

            double vertical = pathVerticalData.get(i);
            Double vagonVertical = (vertical + STAND_ELEVATION_OF_VAGON);
            double zDist = vertical - pathVerticalData.get(i-1);
            if(zDist > 0){//going up

                if(zDist < prevZDist && up){//значит, что подъем стал не таким резким (возможен взлёт)
                    up = false;
                    Double timeOnUp = fullDist/speed;
                    Double verticalSpeed = upDist/timeOnUp;
                    Double horizontalSpeed = Math.sqrt(speed*speed - verticalSpeed*verticalSpeed);
                    Double impulsUp = verticalSpeed * mass;
                    Double forceOnUp = impulsUp/timeOnUp;
                    if(forceOnUp > gravityForce){
                        Log.d("flight", " forceOnUp = " + forceOnUp + "\t gravityForce = " + gravityForce);
                        Double accelerationOnUp = (forceOnUp-gravityForce) / mass;
                        Double timeOnUpFlight = verticalSpeed/9.8;
                        Double totalFlightTime = 2*timeOnUpFlight;
                        Double maxZDist = (verticalSpeed*verticalSpeed)/(2 * 9.8);
                        Double maxXDist = horizontalSpeed * totalFlightTime;

                        Double energyUp = (accelerationOnUp*mass)*maxZDist;
                        Double kinEnergyUp = (verticalSpeed*verticalSpeed*mass)*2;
                        Double pEnergyUp = mass*9.8*maxZDist;

//                        Log.d("flight", " energyUp = " + energyUp);
//                        Log.d("flight", " kinEnergyUp = " + kinEnergyUp);
//                        Log.d("flight", " pEnergyUp = " + pEnergyUp);
//                        Log.d("flight", " xPos = " + xPos);
//                        Log.d("flight", " vagonVertical = " + vagonVertical);
//                        Log.d("flight", " maxZDist = " + maxZDist);
//                        Log.d("flight", " maxXDist = " + maxXDist);
//                        Log.d("flight", " horizontalSpeed = " + horizontalSpeed);
//                        Log.d("flight", " fullDist = " + fullDist);
//                        Log.d("flight", " speed = " + speed);
//                        Log.d("flight", " timeOnUp = fullDist/speed = " + timeOnUp);
//                        Log.d("flight", " upDist = " + upDist);
//                        Log.d("flight", " verticalSpeed = upDist/timeOnUp = " + verticalSpeed);
//                        Log.d("flight", " mass = " + mass);
//                        Log.d("flight", " impulsUp = verticalSpeed * mass = " + impulsUp);


                        List<Entry> flight = calculateFlight(xPos, vagonVertical.floatValue(),
                                horizontalSpeed.floatValue(), verticalSpeed.floatValue(),
                                accelerationOnUp.floatValue(), totalFlightTime.floatValue(),
                                pathVerticalData, maxXDist.floatValue());

                        entriesVagon.addAll(flight);
                        break;
                    }
                }
                double subDist = Math.sqrt(zDist*zDist + 1d);
                fullDist += subDist;
                prevZDist = zDist;
                upDist += zDist;
            }else{
                up = true;
                fullDist = 0;
                upDist = 0;
            }

            Entry entry = new Entry(xPos, Float.parseFloat((vertical + STAND_ELEVATION_OF_VAGON) + ""));
            entriesVagon.add(entry);
            xPos++;
//            if(counter<limit){
//                counter++;
//                lastElements.add(heightZ);
//                sumLastElements += heightZ;
//            }else{
//                lastElements.add(heightZ);
//                sumLastElements += heightZ;
//
//                double first = lastElements.remove(0);
//                sumLastElements -= first;
//            }
//            avg = sumLastElements/counter;
        }
        return entriesVagon;
    }

    private static List<Entry> calculateFlight(float initXPosition, float initZPosition,
                                               float initSpeedX, float initSpeedZ,
                                               float initAcc, float time,
                                               ArrayList<Double> pathVerticalData, float maxXDist){
        float speed = initSpeedZ;
        float zPos = initZPosition;
        float xPos = initXPosition;
        float stoppingAcc = -9.8f;

        List<Entry> entriesPositions = new ArrayList();
        for(float i = 0; i <= time; i+=0.1f) {
            speed = speed + stoppingAcc * 0.1f;
            zPos = zPos + speed * 0.1f;

            xPos = xPos + initSpeedX * 0.1f;
            entriesPositions.add(new Entry(xPos, zPos));
//            Log.d("flight", " x = " + xPos + "\t z = " + zPos + "\t speed = " + speed + "\t time = " + i + "\t stoppingAcc = " + stoppingAcc);

            Float x = xPos;
            int firstPathPos = x.intValue();
            int lastPathPos = firstPathPos+1;
            if(firstPathPos >= pathVerticalData.size() || lastPathPos >= pathVerticalData.size()){
                break;
            }
            Double pathFirstZPos = pathVerticalData.get(firstPathPos);
            Double pathLastZPos = pathVerticalData.get(lastPathPos);

            if(zPos <= pathFirstZPos.floatValue() || zPos <= pathLastZPos.floatValue()){
                break;
            }
        }
        return entriesPositions;
    }
}
