package com.framwork.main.helper;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 *
 */
public class StethoHelper {


    public static <T extends Application> void install(T application) {

        Stetho.initializeWithDefaults(application);
    }
}
