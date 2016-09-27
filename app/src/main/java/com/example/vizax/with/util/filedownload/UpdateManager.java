package com.example.vizax.with.util.filedownload;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.util.DeviceUtils;
import com.example.vizax.with.util.NetWorkUtil;
import com.example.vizax.with.util.RxBus;
import com.example.vizax.with.util.SnackbarUtils;

/**
 * Created by prj on 2016/9/26.
 */

public class UpdateManager {

    private Context mContext;
    private MaterialDialog mAlertDialog;
    private MaterialDialog mConfirmDialog;

    public UpdateManager(Context mContext){
        this.mContext = mContext;
    }

    public void checkUpdate(){

        String version_info = "2.0版本更新";
        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
        int nVersion_code = 2;

        if (mVersion_code<nVersion_code){
            showUpdateDialog(version_info);
        }
    }

    private void showUpdateDialog(String version_info) {
        mAlertDialog = new MaterialDialog.Builder(mContext)
                .title("更新提示")
                .content(version_info)
                .positiveText("立即更新")
                .negativeText("暂不更新")
                .onPositive((dialog, which) -> {
                    String url = "http://117.169.71.165/imtt.dd.qq.com/16891/580B22B6281E704CAB3079BC8210A88D.apk?mkey=57e9226712336c2a&f=8a5d&c=0&fsname=com.supertreasure_1.2_3.apk&csr=4d5s&p=.apk\n";
                    if (!NetWorkUtil.isConnected(mContext)){
                        Toast.makeText(mContext,"大侠请打开网络",Toast.LENGTH_SHORT).show();
                    }else if (NetWorkUtil.isWifiConnected(mContext)) {
                        startService(url);
                    }else {
                        showConfirmDialog(url);
                    }
                })
                .build();
        mAlertDialog.show();
    }

    private void startService(String url){
        Intent intent = new Intent(mContext, DownLoadService.class);
        intent.putExtra("url", url);
        mContext.startService(intent);
    }

    private void showConfirmDialog(String url){
        if (mConfirmDialog==null){
            mConfirmDialog = new MaterialDialog.Builder(mContext)
                    .title("网络提示")
                    .content("当前为移动网络，大侠确定下载更新包吗？")
                    .positiveText("下下下")
                    .negativeText("下次吧")
                    .onPositive((dialog, which) -> startService(url))
                    .build();
        }
        mConfirmDialog.show();
    }
}
