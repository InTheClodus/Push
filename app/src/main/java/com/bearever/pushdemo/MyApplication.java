package com.bearever.pushdemo;

import android.app.Application;
import android.content.Context;

import com.bearever.push.PushTargetManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MyApplication extends Application {
    private static Context context;
    private static RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
//        LeakCanary.install(this);
        PushTargetManager.getInstance().init(this);
//        mRefWatcher=LeakCanary.install(this);
    }

    public static Context getContext() {
        return context;
    }
//    public static RefWatcher getRefWatcher(){
//        return mRefWatcher;
//    }
}
