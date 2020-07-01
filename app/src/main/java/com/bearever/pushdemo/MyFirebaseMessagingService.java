package com.bearever.pushdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

//继承FirebaseMessagingService
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String mToken;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("GOOGLE", "消息服务已开启");
    }

    //获取到谷歌到token
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("Google token", "Refreshed token: $token");
        sendRegistrationToServer(token);
    }

    //  回传给服务器操作
    private void sendRegistrationToServer(String token) {
        mToken = token;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();

                        //谷歌返回的token
                        Log.e("Google_token", token);
                    }
                });

        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            sendNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            sendNotification(getApplicationContext(), remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);

    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {

        //跳转页面
        Intent intent = new Intent(MyApplication.getContext(), InfoActivty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setTicker(messageTitle)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("notification")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}