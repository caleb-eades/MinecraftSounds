package com.foursevengames.minecraftsounds;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class MSApp extends Application {
    private static final String TAG = MSApp.class.getSimpleName();
    private static final int CURRENT_VERSION = 3;
    private static final String KEY_VERSION = "version";

    // Variables used in the app
    private SharedPreferences mSharedPrefs;
    private boolean mIsFirstLaunch = false;
    private boolean mIsUpdateLaunch = false;
    private int mVersion;

    // Lifecycle

    public void onCreate() {

        super.onCreate();
        Log.v(TAG, "onCreate");

        readSharedPrefs();
        writeSharedPrefs();

        Log.d(TAG, "IS_FIRST_APP_LOAD : " + (mIsFirstLaunch ? "TRUE" : "FALSE"));
        Log.d(TAG, "IS_UPDATE_APP_LOAD : " + (mIsUpdateLaunch ? "TRUE" : "FALSE"));

    }

    // Data management methods

    private void readSharedPrefs() {

        mSharedPrefs = getSharedPreferences(TAG, MODE_PRIVATE);

        mVersion = mSharedPrefs.getInt(KEY_VERSION, CURRENT_VERSION);

        if (mVersion == CURRENT_VERSION) {

            mIsFirstLaunch = true;

        } else if (mVersion < CURRENT_VERSION) {

            mIsUpdateLaunch = true;

        }

    }

    private void writeSharedPrefs() {

        SharedPreferences.Editor editor = mSharedPrefs.edit();

        editor.putInt(KEY_VERSION, CURRENT_VERSION);

        editor.apply();

    }

    // Get/Set methods

    /**
     * @return true if first user load
     */
    public boolean getIsFirstLaunch() {

        return mIsFirstLaunch;

    }

    /**
     * @return true if first load after update
     */
    public boolean getIsUpdateLaunch() {

        return mIsUpdateLaunch;

    }

}
