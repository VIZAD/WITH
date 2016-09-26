package com.example.vizax.with.util.filedownload;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.util.DeviceUtils;

/**
 * Created by prj on 2016/9/26.
 */

public class UpdateManager {

    private Context mContext;
    private MaterialDialog mAlertDialog;

    public UpdateManager(Context mContext){
        this.mContext = mContext;
    }

    public void checkUpdate(){

        String version_info = "更新内容\n" + " 2.0版本更新";
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
                .onPositive((dialog, which) -> mContext.startService(new Intent(mContext, DownLoadService.class)))
                .build();
        mAlertDialog.show();
    }
}
