package com.example.annah.cityguide;

/**
 * Created by Anik on 4/9/2018.
 */

public class TransportationItem {
    private String mTransportationName;
    private int mImageOfTheTransportation;

    public TransportationItem(String transportationName, int imageOfTheTransportation){
        mTransportationName = transportationName;
        mImageOfTheTransportation = imageOfTheTransportation;
    }

    public String getNameOfTheTransportation() {
        return mTransportationName;
    }

    public void setmTransportationName(String mTransportationName) {
        this.mTransportationName = mTransportationName;
    }

    public int getmImageOfTheTransportation() {
        return mImageOfTheTransportation;
    }

    public void setmImageOfTheTransportation(int mImageOfTheTransportation) {
        this.mImageOfTheTransportation = mImageOfTheTransportation;
    }
}
