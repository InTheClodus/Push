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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushTargetManager.getInstance().init(getApplication()).addPushReceiverListener("ml",new PushTargetManager.OnPushReceiverListener() {
            @Override
            public void onRegister(ReceiverInfo info) {

            }

            @Override
            public void onAlias(ReceiverInfo info) {

            }

            @Override
            public void onMessage(ReceiverInfo info) {

            }

            @Override
            public void onNotification(ReceiverInfo info) {

            }

            @Override
            public void onOpened(ReceiverInfo info) {

            }
        });

        PushBroadcastReceiverIml pushBroadcastReceiverIml=new PushBroadcastReceiverIml();
        IntentFilter filter= new IntentFilter();
        filter.addAction("com.bearever.push.RECEIVER");
        registerReceiver(pushBroadcastReceiverIml,filter);
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

}
