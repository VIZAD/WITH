package com.example.vizax.with.util.filedownload;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.VersionBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.DeviceUtils;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.NetWorkUtil;
import com.example.vizax.with.util.PrjOkHttpUtil;
import com.example.vizax.with.util.RxBus;
import com.example.vizax.with.util.SnackbarUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

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

        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
        PrjOkHttpUtil.addToken()
                .url(APIConstant.getApi(APIConstant.USER_VERSIONUPDATE))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        VersionBean versionBean = GsonUtil.toString(response,VersionBean.class);
                        if (versionBean.getCode()==200){
                            if (mVersion_code<versionBean.getData().getVersionCode()){
                                showUpdateDialog(versionBean.getData().getVersionContent(),versionBean.getData().getVersionUrl());
                            }else {
                                Toast.makeText(mContext,versionBean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(mContext,versionBean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showUpdateDialog(String version_info,String version_url) {

        String[] item = version_info.split(";");
        mAlertDialog = new MaterialDialog.Builder(mContext)
                .title("更新提示")
                .items((CharSequence[]) item)
                .positiveText("立即更新")
                .negativeText("暂不更新")
                .onPositive((dialog, which) -> {
                    if (!NetWorkUtil.isConnected(mContext)){
                        Toast.makeText(mContext,"大侠请打开网络",Toast.LENGTH_SHORT).show();
                    }else if (NetWorkUtil.isWifiConnected(mContext)) {
                        startService(version_url);
                    }else {
                        showConfirmDialog(version_url);
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
