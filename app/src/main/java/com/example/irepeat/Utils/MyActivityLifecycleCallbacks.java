package com.example.irepeat.Utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.io.Serializable;

public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks, Serializable {
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    public static boolean isForeground() {
        return resumed > paused;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        started++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        resumed++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        stopped++;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
