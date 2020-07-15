
[原作地址] (https://github.com/Luomingbear/Push)
 
该项目仅是在使用这个项目时出现的问题作出的一些改动

##1.配置APPKYEY
原作者是在push包中直接添加key，这里作出的改动是将push的key删除，改为在 app/build.gradle中添加
```properties
defaultConfig {
        manifestPlaceholders = [
                "jPushAppkey"     : "ea6c961e572ad972df4ef456",//极光推送的AppKey
                "huaweiPushAppId" : "100698925",//华为推送的AppId
                "xiaomiPushAppkey": "55617974 54571",//"57417800 14675",//小米推送的AppKey****请务必在数值中间添加一个空格，否则会发生数值变化****
                "xiaomiPushAppId" : "28823037 61517974571",//"28823037 61517800675",//小米推送的AppID****请务必在数值中间添加一个空格，否则会发生数值变化****
                "oppoAppKey"      : "9d24177594c741be86465ec9fe490a44",//OPPO推送的appKey
                "oppoAppSecret"   : "c60bec1f803e49e88037562ec2109286",//OPPO推送的appSecret
                "meizuAppId"      : "1200 55",//魅族推送AppId ****请务必在数值中间添加一个空格，否则会发生数值变化****
                "meizuAppKey"     : "3aca7449444347c4a1d2a70826ae1e9b",//魅族推送AppKey
                "vivoAppId"       : "11937",//vivo推送AppId
                "vivoAppKey"      : "acfdb480-e178-4098-80ae-76284df7d588",//vivo推送AppKey
                "marketChannel"   : "develops"//渠道
        ]
}
```
##2.配置华为推送
华为推送的SDK是通过工具生成的，所以需要手动配置生成代码，先下载[华为HMS Agent套件](https://obs.cn-north-2.myhwclouds.com/hms-ds-wf/sdk/HMSAgent_2.6.1.302.zip)，
解压出来之后根据自己的电脑系统需要相应的脚本文件，例如：Window点击双击GetHMSAgent_cn.bat
在弹出的窗口里面根据提示操作就好了，最后生存的代码会保存在copysrc文件夹里面。
将生成的代码复制到Push的java目录下面，res资源文件根据需要添加到资源目录下。
##3.初始化
在Application的onCreate中调用 PushTargetManager的init方法
```java
    PushTargetManager.getInstance().init(this);
```
或者在MainActivty的onCreate中调用
```java
 PushTargetManager.getInstance().init(getApplication());
```
##4.处理推送消息
#4.1自定义一个广播接收器。继承push里的BasePushBroadcastReceiver
```java
public class PushBroadcastReceiverIml extends BasePushBroadcastReceiver {
    private static String TAG="PushBroadcastReceiverIml";
    public PushBroadcastReceiverIml() {
    }

    @Override
    public void onRegister(Context context, ReceiverInfo info) {
        Log.d(TAG, "推送注册成功:"+"平台："+info.getPushTarget()+"  标题："+info.getTitle()+"   注册ID："+info.getContent());

    }

    @Override
    public void onAlias(Context context, ReceiverInfo info) {
        Log.d(TAG, "设置了别名\n"+info);
    }

    @Override
    public void onMessage(Context context, ReceiverInfo info) {
        Log.d(TAG, "收到自定义消息\n"+info.getContent()+"\n"+info.getTitle());
    }

    @Override
    public void onNotification(Context context, ReceiverInfo info) {
        Log.d(TAG, "收到通知\n"+info);
    }

    @Override
    public void onOpened(Context context, ReceiverInfo info) {
        Log.d(TAG, "点击了通知\n");
        /*Intent intent=new Intent(MyApplication.getContext(),InfoActivty.class);
        if (!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);*/
    }
}
```
在AndroiManifest.xml中注册广播接收器
```xml
<manifest>
	<permission
        android:name="${applicationId}.push.RECEIVER"
        android:protectionLevel="signature" />
    <uses-permission android:name="${applicationId}.push.RECEIVER" />
    
    <application>
    	<receiver
            android:name=".PushBroadcastReceiverIml"
            android:permission="${applicationId}.push.RECEIVER">
            <intent-filter>
                <action android:name="com.bearever.push.IPushBroadcast" />
            </intent-filter>
        </receiver>
    </application>
</manifest>
```
请注意 ${applicationId} 此处替换成你的包名，不过如果你的build.gradle有这个，那你也可以直接这样使用，否则你应该这么写
如：你的包名是 com.test.pushdemo
那么上面的 ${applicationId}.push.RECEIVER 就改写做 com.test.pushdemo.push.RECEIVER;
action 节点的name是push中隐式广播的action不用管

在MainActivity中动态注册广播接收器
```java
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
```
#4.24.2addPushReceiverListener
```java
PushTargetManager.getInstance().addPushReceiverListener("ml",
                new PushTargetManager.OnPushReceiverListener() {
                    @Override
                    public void onRegister(ReceiverInfo info) {
                        Log.d(TAG, "注册成功\n" + info.toString());
                    }

                    @Override
                    public void onAlias(ReceiverInfo info) {
                        Log.d(TAG, "设置了别名\n" + info.toString());
                    }

                    @Override
                    public void onMessage(ReceiverInfo info) {
                        Log.d(TAG, "收到了自定义消息\n" + info.toString());
                    }

                    @Override
                    public void onNotification(ReceiverInfo info) {
                        Log.d(TAG, "收到了通知\n" + info.toString());
                    }

                    @Override
                    public void onOpened(ReceiverInfo info) {
                        Log.d(TAG, "点击通知\n" + info.toString());
                    }
                });
```
这个方法只会在app运行期间游戏，一旦被杀死，就不起作用了/