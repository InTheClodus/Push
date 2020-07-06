package com.bearever.pushdemo;

import android.app.Application;
import android.content.Context;

import com.bearever.push.PushTargetManager;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        PushTargetManager.getInstance().init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for
            // heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);

    }

    public static Context getContext() {
        return context;
    }
}
