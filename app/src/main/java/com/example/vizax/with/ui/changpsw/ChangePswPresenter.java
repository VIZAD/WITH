package com.example.vizax.with.ui.changpsw;

import android.support.annotation.NonNull;

import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by xujianbo on 2016/9/22.
 */
public class ChangePswPresenter implements ChangePswContact.changepswPresent {
    private ChangePswContact.View changepasswordView;
    private ChangePswModel changePswModel;

    public ChangePswPresenter(){
        changePswModel = new ChangePswModel();
    }



    @Override
    public void attachView(@NonNull ChangePswContact.View View) {
          changepasswordView = View;
    }

    @Override
    public void detachView() {
            changepasswordView = null;
    }

    @Override
    public void changepsw(String oldpassword, String newpassword) {
        changepasswordView.showLoading();
        changePswModel.changepsw(oldpassword, newpassword, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                changepasswordView.dimissloading();
                changepasswordView.changepswFailure(e+"");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseBean baseBean = GsonUtil.toString(response,BaseBean.class);
                if(baseBean.getCode().equals("200")){
                    System.out.println(baseBean.getMsg());
                    changepasswordView.changepswSuccess("修改成功");
                }

                else
                    changepasswordView.changepswFailure("修改失败");
                changepasswordView.dimissloading();
            }
        });

    }
}
