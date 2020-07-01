package com.bearever.push.target.google;

import android.app.Application;
import android.content.Context;

import com.bearever.push.model.ReceiverInfo;
import com.bearever.push.target.BasePushTargetInit;

public class GoogleInit extends BasePushTargetInit {
    private static final  String TAG="GOOGLEInit";
    /**
     * 推送初始化
     *
     * @param application
     */
    public GoogleInit(Application application) {
        super(application);

    }

    @Override
    public void setAlias(Context context, String alias, ReceiverInfo registerInfo) {
        super.setAlias(context, alias, registerInfo);
    }
}
