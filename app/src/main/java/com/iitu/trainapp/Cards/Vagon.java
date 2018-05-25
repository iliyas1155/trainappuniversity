package com.iitu.trainapp.Cards;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Vagon is an item inside the recycler view
 * it holds information about vagon in sequence
 */
public class Vagon implements Parcelable {
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

    protected Vagon(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            mass = null;
        } else {
            mass = in.readDouble();
        }
    }

    public static final Creator<Vagon> CREATOR = new Creator<Vagon>() {
        @Override
        public Vagon createFromParcel(Parcel in) {
            return new Vagon(in);
        }

        @Override
        public Vagon[] newArray(int size) {
            return new Vagon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        if (mass == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(mass);
        }
    }
}
