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

import com.cm.retrofit2.converter.file.body.ProgressResponseListener;
import com.example.vizax.with.R;
import com.example.vizax.with.util.RxBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.subscriptions.CompositeSubscription;


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
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Retrofit.Builder retrofit;
    private String apkUrl;

    public DownLoadService(){
        apkUrl = "http://112.124.9.133:8080/parking-app-admin-1.0/android/manager/adminVersion/";
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
                .url("http://117.169.71.165/imtt.dd.qq.com/16891/580B22B6281E704CAB3079BC8210A88D.apk?mkey=57e9226712336c2a&f=8a5d&c=0&fsname=com.supertreasure_1.2_3.apk&csr=4d5s&p=.apk\n")
                .build()
                .execute(new FileCallBack(destFileDir,destFileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Log.w("haha","finish");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.w("haha",progress+"!!!!"+total);
                    }
                });
        /*if (retrofit==null){
            retrofit = new Retrofit.Builder();
        }
        retrofit.baseUrl("http://117.169.71.165/imtt.dd.qq.com/16891/580B22B6281E704CAB3079BC8210A88D.apk?mkey=57e9226712336c2a&f=8a5d&c=0&fsname=com.supertreasure_1.2_3.apk&csr=4d5s&p=.apk\n")
                .client(OkHttpUtils.getInstance().getOkHttpClient())
                .build()
                .create(ApiService.class)
                .downloadWithDynamicUrl()
                .enqueue(new FileCallback(destFileDir,destFileName) {
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
                             public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                 cancelNotification();
                             }
                         });*/
        /*apiService = DownLoadRetrofitClient.createResponseService(ApiService.class,
                "http://112.124.9.133:8080/parking-app-admin-1.0/android/manager/adminVersion/",
                (bytesRead, contentLength, done) -> updateNotification(bytesRead*100/contentLength));*/

      /*  apiService.downloadWithDynamicUrl()
                */
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
