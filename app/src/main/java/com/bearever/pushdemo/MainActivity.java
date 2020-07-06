package com.bearever.pushdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bearever.push.PushTargetManager;
import com.bearever.push.model.ReceiverInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static String TAG="MainActivty";

    private PushBroadcastReceiverIml pushBroadcastReceiverIml;
    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushTargetManager.getInstance().init(getApplication());
//        intentFilter=new IntentFilter();
//        intentFilter.addAction("com.bearever.pushdemo.PushBroadcastReceiverIml");
//        pushBroadcastReceiverIml=new PushBroadcastReceiverIml();
//        registerReceiver(pushBroadcastReceiverIml,intentFilter);
//
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pushBroadcastReceiverIml);
        Runtime.getRuntime().gc();
    }
}
