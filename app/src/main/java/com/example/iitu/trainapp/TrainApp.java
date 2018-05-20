package com.example.iitu.trainapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class TrainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Stolzl-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
