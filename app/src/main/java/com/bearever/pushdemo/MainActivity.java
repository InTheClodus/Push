package com.bearever.pushdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bearever.push.PushTargetManager;
import com.bearever.push.model.ReceiverInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static String TAG="MainActivty";
    /**
     * intentFilter 意图过滤器，设置自身需要监听的广播信号
     */
    private IntentFilter intentFilter;
    /**
     * localBroadcastManager 本地广播管理
     */
    private LocalBroadcastManager localBroadcastManager;
    /**
     * localReceiver 本地广播接收器
     */
    private PushBroadcastReceiverIml localReceiver;
    private PushBroadcastReceiverIml pushBroadcastReceiverIml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取一个本地广播管理的对象
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // 本地广播的发送者
        Button button = findViewById(R.id.testBroadcast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.bearever.push.IPushBroadcast");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        // 设置自身需要监听的广播信号
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.bearever.push.IPushBroadcast");
        // 注册本地广播监听器
        localReceiver = new PushBroadcastReceiverIml();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        //<editor-fold desc="谷歌推送">
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();

                        // Log and toast

                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        //</editor-fold>
//
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"停止");
        unregisterReceiver(pushBroadcastReceiverIml);
        Runtime.getRuntime().gc();
    }
    //LocalReceiver 内部类，实现了onReceive方法
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "收到了本地发出的广播", Toast.LENGTH_SHORT).show();
        }
    }
}
