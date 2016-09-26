package com.example.vizax.with.util.filedownload;

import com.example.vizax.with.util.RxBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by prj on 2016/9/26.
 */

public abstract class FileCallback implements Callback<ResponseBody>{

    /**
     * 订阅下载进度
     */
    private CompositeSubscription rxSubscriptions = new CompositeSubscription();

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;

    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir,String destFileName){
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    private void  subscribeLoadProgress(){
        rxSubscriptions.add(RxBus.getDefault()
            .toObservable(FileBean.class)
            .onBackpressureBuffer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fileBean -> {
                onLoading(fileBean.getProgress(),fileBean.getTotal());
            }));
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            saveFile(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File saveFile(Response<ResponseBody> response) throws Exception{

        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buf = new byte[2048*10];
        int len ;

        try {
            File dir = new File(destFileDir);
            if (!dir.exists()){// 如果文件不存在新建一个
                dir.mkdirs();
            }
            is = response.body().byteStream();
            File file = new File(destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf))!=-1){
                fos.write(buf,0,len);
            }
            onSuccess(file);
            unSubscribe();
            return file;
        }finally{
            is.close();
            fos.close();
        }
    }

    /**
     * 成功后回调
     */
    public abstract void onSuccess(File file);

    /**
     * 下载过程回调
     */
    public abstract void onLoading(long progress, long total);

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unSubscribe() {
        if (!rxSubscriptions.isUnsubscribed()) {
            rxSubscriptions.unsubscribe();
        }
    }
}
