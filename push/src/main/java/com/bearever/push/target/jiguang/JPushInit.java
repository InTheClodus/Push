package com.bearever.push.target.jiguang;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的初始化服务
 */

public class JPushInit extends BasePushTargetInit {
    private static final String TAG = "JPushInit";

    public JPushInit(Application application) {
        super(application);
        JPushInterface.init(application);
    }

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        JPushInterface.setAlias(context, 0, alias);
    }
}
