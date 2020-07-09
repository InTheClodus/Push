package com.bearever.pushdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.receiver.BasePushBroadcastReceiver;

public class PushBroadcastReceiverIml extends BasePushBroadcastReceiver {
    private static String TAG="PushBroadcastReceiverIml";
    public PushBroadcastReceiverIml() {
    }

    @Override
    public void onRegister(Context context, ReceiverInfo info) {
        Log.d(TAG, "推送注册成功\n");
    }

    @Override
    public void onAlias(Context context, ReceiverInfo info) {
        Log.d(TAG, "设置了别名\n");
    }

    @Override
    public void onMessage(Context context, ReceiverInfo info) {
        Log.d(TAG, "收到自定义消息\n");
    }

    @Override
    public void onNotification(Context context, ReceiverInfo info) {
        Log.d(TAG, "收到通知\n");
    }

    @Override
    public void onOpened(Context context, ReceiverInfo info) {
        Log.d(TAG, "点击了通知\n");
        Intent intent=new Intent(MyApplication.getContext(),InfoActivty.class);
        context.startActivity(intent);
    }
}
