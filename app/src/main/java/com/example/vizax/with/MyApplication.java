package com.example.vizax.with;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by prj on 2016/8/15.
 */

public class MyApplication extends Application {

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    @Override
    public void onCreate() {
        super.onCreate();
        initReservoir();
        initOkHttpClient();
        initUmengPush();
    }

    private void initReservoir() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("WITH"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUmengPush() {
        //请勿在调用register方法时做进程判断处理（主进程和channel进程均需要调用register方法才能保证
        // 长连接的正确建立）。
        /*若有需要，可以在Application的onCreate方法中创建一个子线程，并把mPushAgent.register这一行
        代码放到该子线程中去执行（请勿将PushAgent.getInstance(this)放到子线程中）。
        device token是友盟生成的用于标识设备的id，长度为44位，不能定制和修改。同一台设备上不同应用对
        应的device token不一样。
        如需手动获取device token，可以调用mPushAgent.getRegistrationId()方法（需在注册成功后调用）。*/
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //关闭日志输出
        mPushAgent.setDebugMode(false);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                //注册成功会返回device token
                //Log.w("haha","token="+s);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        /*msg.builder_id是服务器下发的消息字段，用来指定通知消息的样式。开发者可在友盟Push网站上指定，默认值为0*/
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon,
                                getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /*通知免打扰模式,SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯
        setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute)*/
        mPushAgent.setNoDisturbMode(23, 0, 7, 0);

        //一分钟内最多的通知条数，多了会覆盖
        mPushAgent.setMuteDurationSeconds(2);

        /*mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动 */

        /*如果在发送后台没有指定通知栏图标，SDK 将使用本地的默认图标，其中，大图标默认使用：
        drawable下的umeng_push_notification_default_large_icon，小图标默认使用drawable下的
        umeng_push_notification_default_small_icon,若开发者没有设置这两个图标，则默认使用应用的图标
                ( <application android:icon="@drawable/ic_launcher"> )。 请确保应用设置了默认图标*/

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //SDK通过Thread.UncaughtExceptionHandler  捕获程序崩溃日志，并在程序下次启动时发送到服务器。
        // 如不需要错误统计功能，可通过此方法关闭
        MobclickAgent.setCatchUncaughtExceptions(false);
    }

}
