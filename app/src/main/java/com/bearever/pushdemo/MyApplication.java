package com.bearever.pushdemo;

import android.app.Application;
import android.content.Context;

import com.bearever.push.PushTargetManager;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        PushTargetManager.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }
}
