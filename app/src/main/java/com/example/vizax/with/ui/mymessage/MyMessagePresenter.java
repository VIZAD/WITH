package com.example.vizax.with.ui.mymessage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.MyMessageBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Xuhai on 2016/9/23.
 */

public class MyMessagePresenter implements MyMessageContact.Presenter {
    private MyMessageContact.View mMessageView;
    private Context mContext;

    public MyMessagePresenter(Context mContext) {
        this.mContext = mContext;
        mMessageModel = new MyMessageModel();
        mMessageBeenLists = new ArrayList<>();
    }

    private MyMessageContact.Modle mMessageModel;
    private MyMessageBean mMessageBean;
    public ArrayList<MyMessageBean.DataBean> mMessageBeenLists;
    private int lastId;



    @Override
    public void attachView(@NonNull MyMessageContact.View View) {
        this.mMessageView = View;
    }

    @Override
    public void detachView() {
        this.mMessageView = null;
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

    @Override
    public void deleteMessage(MyMessageAdapter mMessageAdapter, int adapterPosition, int messageId) {

        mMessageModel.deleteMessage(SharedUtil.getString(mContext, FieldConstant.token),messageId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().remove(adapterPosition);
                    mMessageAdapter.getmDatas().get(adapterPosition).setReaded(true);
                    mMessageAdapter.notifyItemRemoved(adapterPosition);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });

    }

    @Override
    public void readMessage(MyMessageAdapter mMessageAdapter, int adapterPosition, int messageId) {
        mMessageModel.readMessage(SharedUtil.getString(mContext, FieldConstant.token),messageId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    //mMessageAdapter.getmDatas().remove(adapterPosition);
                    mMessageAdapter.getmDatas().get(adapterPosition).setReaded(true);
                    mMessageAdapter.notifyItemChanged(adapterPosition);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });
    }

    @Override
    public void agreeMessage(MyMessageAdapter mMessageAdapter, int position, int messageId) {
        mMessageModel.agreeMessage(
                SharedUtil.getString(mContext, FieldConstant.token)
                ,messageId
                ,true
                ,mMessageAdapter.getmDatas().get(position).getInvationId()
                ,mMessageAdapter.getmDatas().get(position).getApplyUserId()
                , new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().remove(position);
                    mMessageAdapter.notifyItemRemoved(position);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });
    }
    @Override
    public void rejectMessage(MyMessageAdapter mMessageAdapter, int position, int messageId) {
        mMessageModel.agreeMessage(
                SharedUtil.getString(mContext, FieldConstant.token)
                ,messageId
                ,false
                ,mMessageAdapter.getmDatas().get(position).getInvationId()
                ,mMessageAdapter.getmDatas().get(position).getApplyUserId()
                , new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().remove(position);
                    mMessageAdapter.notifyItemRemoved(position);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });
    }


}
