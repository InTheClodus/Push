package com.bearever.pushdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.bearever.push.PushTargetManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static String TAG="MainActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushTargetManager.getInstance().init(getApplication());
        // 获取一个本地广播管理的对象
        /**
         * localBroadcastManager 本地广播管理
         */
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // 设置自身需要监听的广播信号
        /**
         * intentFilter 意图过滤器，设置自身需要监听的广播信号
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.bearever.push.IPushBroadcast");
        // 注册本地广播监听器
        /**
         * localReceiver 本地广播接收器
         */
        PushBroadcastReceiverIml pushBroadcastReceiverIml = new PushBroadcastReceiverIml();
        localBroadcastManager.registerReceiver(pushBroadcastReceiverIml, intentFilter);

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
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"停止");
//        unregisterReceiver(pushBroadcastReceiverIml);
        Runtime.getRuntime().gc();
    }
}
