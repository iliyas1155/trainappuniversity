package com.iitu.trainapp.Cards;
/**
 * Path is an item inside the paths recycler view
 * it holds info about path, which gets from Firebase
 */

import java.util.ArrayList;

public class Path {
    public String name;
    public String path_id;
    public long length;
    public boolean is_valid;
    public ArrayList<Double> vertical_data = null;
    public Path(String path_id, String name, long length, boolean is_valid) {
        this.path_id = path_id;
        this.name = name;
        this.length = length;
        this.is_valid = is_valid;
    }

    public void setDataFromDb(ArrayList<Double> vertical_data){
        this.vertical_data = vertical_data;
    }
}