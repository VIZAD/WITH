package com.example.vizax.with.ui.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.SharedUtil;

/**
 * Created by Administrator on 2016/10/1.
 */
public class MainPresenter implements MainContact.Presenter {
    private Context context;
    private MainContact.View view;

    public MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public boolean isFirstLogin() {
        return SharedUtil.getBoolean(context, FieldConstant.isfirstlogin,true);
    }

    @Override
    public boolean isHadLogin() {
        return SharedUtil.getBoolean(context,FieldConstant.ishadlogin,false);
    }

    @Override
    public void attachView(@NonNull MainContact.View View) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
