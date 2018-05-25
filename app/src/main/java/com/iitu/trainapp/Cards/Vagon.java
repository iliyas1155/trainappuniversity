package com.iitu.trainapp.Cards;

/**
 * Vagon is an item inside the recycler view
 * it holds information about vagon in sequence
 */
public class Vagon {
    public int id;
    public Double mass;
    public Vagon(int id) {
        this.id = id;
        this.mass = null;
    }
    public Vagon(int id, Double mass) {
        this.id = id;
        this.mass = mass;
    }
}
