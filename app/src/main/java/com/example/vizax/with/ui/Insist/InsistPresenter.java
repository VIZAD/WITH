package com.example.vizax.with.ui.Insist;

import android.animation.Animator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.example.vizax.with.R;
import com.example.vizax.with.bean.BaseBean;
import com.example.vizax.with.bean.Misson_bg_colorSet;
import com.example.vizax.with.bean.taskNumber;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.demo.DemoContact;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.sidemenu.interfaces.Resourceble;
import com.example.vizax.with.util.sidemenu.interfaces.ScreenShotable;
import com.example.vizax.with.util.sidemenu.model.SlideMenuItem;
import com.example.vizax.with.util.sidemenu.util.ViewAnimator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by VIZAX on 2016/09/16.
 */

public class InsistPresenter implements InsistContact.Presenter {


    private List<SlideMenuItem> list = new ArrayList<>();
    private Misson_bg_colorSet mMssion_bg_colorSet;
    private SharedPreferences sp;
    private InsistContact.View InsistView;
    private Activity activity;
    private ContentFragment contentFragment;


    public InsistPresenter() {
    }

    @Override
    public void getTaskCount() {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.KEEP_GETTASKCOUNT))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        /*Gson解析已经封装，下次把User改成自己对应的Bean即可
                          默认状态码200为成功*/
                        BaseBean<taskNumber> baseBean = GsonUtil.toString(response,BaseBean.class);
//                        if (baseBean.getCode().equals("200")) {
//                            //System.out.println("number:"+baseBean.getData().getTaskNumber());
//                        }
//                        else {
//
//                        }
                        System.out.println("bean = "+baseBean.getCode());
                    }
                });

    }

    @Override
    public void attachView(@NonNull InsistContact.View View) {

    }

    @Override
    public void detachView() {

    }
}


