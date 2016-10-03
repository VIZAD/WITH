package com.example.vizax.with.util.filedownload;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.vizax.with.R;
import com.example.vizax.with.util.RxBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by prj on 2016/9/26.
 */

public class DownLoadService extends Service {

    public static String destFileDir = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+ File.separator+"M_DEFAULT_DIR";
    /**
     * 目标文件存储的文件名
     */
    public static String destFileName = "with.apk";
    private Context mContext;
    private String url;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        url = intent.getStringExtra("url");
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadFile() {
        initNotification();

        /*RxBus.getDefault().toObservable(UpdateMessage.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateMessage -> {
                    destFileDir = updateMessage.getDestFileDir();
                    destFileName = updateMessage.getDestFileName();
                    url = updateMessage.getUrl();
                });*/

        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(destFileDir,destFileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        cancelNotification();
                    }

                    @Override
                    public void onResponse(File response, int id) {}

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        updateNotification((int)(progress*100));
                        if (progress==1.0){
                            cancelNotification();
                            installApk(new File(destFileDir+"/"+destFileName));
                        }
                    }
                });
    }

    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 打开apk进行安装
        mContext.startActivity(install);
    }

    private void updateNotification(int progress) {

        if (preProgress < progress){
            mBuilder.setContentText(progress+"%");
            mBuilder.setProgress(100, progress,false);
            mNotificationManager.notify(NOTIFY_ID,mBuilder.build());
        }
        preProgress = progress;
    }

    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.umeng_push_notification_default_small_icon)
                .setContentText("0%")
                .setContentTitle("WITH版本更新")
                .setOngoing(true)
                .setProgress(100,0,false);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

    private void cancelNotification() {
        mNotificationManager.cancel(NOTIFY_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
