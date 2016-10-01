package com.example.vizax.with.ui.mymessage;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.bean.MyMessageBean;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Xuhai on 2016/9/23.
 */

public class MyMessagePresenter implements MyMessageContact.Presenter {
    private MyMessageContact.View mMessageView;
    private MyMessageModel mMessageModel;
    private MyMessageBean mMessageBean;
    public ArrayList<MyMessageBean.DataBean> mMessageBeenLists;
    private int lastId;

    public MyMessagePresenter(MyMessageActivity mMessageActivity) {
        this.mMessageView = mMessageActivity;
        mMessageModel = new MyMessageModel();
        mMessageBeenLists = new ArrayList<>();
    }

    @Override
    public void attachView(@NonNull MyMessageContact.View View) {

    }

    @Override
    public void detachView() {

    }

    //填充数据
    @Override
    public void loadMessageData(String token, int lastMessageId, int limit) {
        mMessageModel.loadMyMessageData(token, lastMessageId, limit, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                mMessageBean = GsonUtil.toString(response, MyMessageBean.class);
                Log.i("xxx", mMessageBean.toString());

                if (mMessageBean.getCode().equals("200")) {
                    if (lastMessageId == 0) {
                        mMessageBeenLists.clear();
                    }
                    for (int i = 0; i < mMessageBean.getData().size(); i++) {
                        MyMessageBean.DataBean dataBean = mMessageBean.getData().get(i);
                        mMessageBeenLists.add(dataBean);
                        if (i == mMessageBean.getData().size() - 1) {
                            lastId = dataBean.getInvationId();
                        }
                    }

                    mMessageView.loadDatas(mMessageBeenLists,lastId);
                }
            }
        });
    }


}
