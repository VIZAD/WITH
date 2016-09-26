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

import com.example.vizax.with.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.Call;


/**
 * Created by prj on 2016/9/26.
 */

public class DownLoadService extends Service {

    private String destFileDir = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+ File.separator+"M_DEFAULT_DIR";
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "with.apk";
    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private ApiService apiService;
    private String apkUrl;

    public DownLoadService(String destFileDir,String destFileName,String apkUrl){
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        this.apkUrl = apkUrl;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

    private void loadFile() {
        initNotification();

        OkHttpUtils.get()
                .url(apkUrl)
                .build()
                .execute(new FileCallBack(destFileDir, destFileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();
                        cancelNotification();
                    }

                    @Override
                    public void onResponse(File response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        if (progress==total){
                            cancelNotification();
                            installApk(new File(destFileName));
                        }else {
                            updateNotification((long)(progress * 100 / total));
                        }
                    }
                });

        /*apiService = DownLoadRetrofitClient.createResponseService(
                ApiService.class, new FileCallback.onDownLoadListener() {
                    @Override
                    public void onSuccess(File file) {
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        cancelNotification();
                    }
                });

        apiService.downloadWithDynamicUrl(apkUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())*/
    }

    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        mContext.startActivity(install);
    }

    private void updateNotification(long progress) {
        int currentProgress = (int)progress;
        if (preProgress < currentProgress){
            mBuilder.setContentText(progress+"%");
            mBuilder.setProgress(100,(int)progress,false);
            mNotificationManager.notify(NOTIFY_ID,mBuilder.build());
        }
        preProgress = (int)progress;
    }

    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.umeng_push_notification_default_small_icon)
                .setContentText("0%")
                .setContentTitle("WITH版本更新")
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
