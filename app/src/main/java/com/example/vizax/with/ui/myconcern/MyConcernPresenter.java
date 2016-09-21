package com.example.vizax.with.ui.myconcern;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.vizax.with.R;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.ui.Insist.InsistContact;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.example.vizax.with.constant.APIConstant.INVITATION_GETCONCERNEDUSERS;

/**
 * Created by VIZAX on 2016/09/21.
 */

public class MyConcernPresenter implements MyConcernContact.Presenter  {


    private List<MyConcern.DataBean> mDatas;
    private  MyConcernContact.View fragment;

    @Override
    public void getMyCocernData(Context context) {

        OkHttpUtils.post()
                .url(APIConstant.getApi(INVITATION_GETCONCERNEDUSERS))
                .addParams("token","token")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context,e+"",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mDatas = new ArrayList<MyConcern.DataBean>();
                        MyConcern myConcern = GsonUtil.toListString(response,MyConcern.class);
                        System.out.println("myConcern = "+myConcern.getData().get(0).getHeadUrl());
                        for (int i = 0; i <10; i++) {
                            MyConcern.DataBean dataBean = new MyConcern.DataBean( myConcern.getData().get(0).getConcernedUserId(),
                                    true, myConcern.getData().get(0).getName(),myConcern.getData().get(0).getHeadUrl());
                            mDatas.add(dataBean);
                            System.out.println("m"+mDatas);
                        }
                        fragment.setData(mDatas);

                    }

                });
    }

    @Override
    public void attachView(@NonNull MyConcernContact.View View) {
        fragment = View;
    }

    @Override
    public void detachView() {

    }

}
