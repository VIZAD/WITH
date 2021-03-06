package com.example.vizax.with.ui.mymessage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.App;
import com.example.vizax.with.bean.BaseEmptyBean;
import com.example.vizax.with.bean.MyMessageBean;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.ReLoginUtil;
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

    }

    private MyMessageContact.Modle mMessageModel;

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
    /*@Override
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
                }else if (mMessageBean.getCode().equals("499")){
                    mMessageView.startLoginActivity();
                    //ReLoginUtil.relogin();
                }
            }
        });
    }*/

    @Override
    public void deleteMessage(MyMessageAdapter mMessageAdapter, int adapterPosition, int messageId) {

        mMessageModel.deleteMessage(SharedUtil.getString(mContext, FieldConstant.token),messageId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mMessageView!=null)
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().getData().remove(adapterPosition);
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
                if (mMessageView!=null)
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    //mMessageAdapter.getmDatas().remove(adapterPosition);
                    mMessageAdapter.getmDatas().getData().get(adapterPosition).setReaded(true);
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
                ,mMessageAdapter.getmDatas().getData().get(position).getInvationId()
                ,mMessageAdapter.getmDatas().getData().get(position).getApplyUserId()
                , new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mMessageView!=null)
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().getData().remove(position);
                    mMessageAdapter.notifyItemRemoved(position);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });
    }

    @Override
    public void OnPullRefresh(MyMessageAdapter mMessageAdapter) {
        mMessageModel.loadMyMessageData(
                SharedUtil.getString(App.instance,FieldConstant.token)
                , 10000000, 10, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mMessageView!= null)//因为activity关掉的时候，activity调用了detach，activity会被回收。这里的引用会为null
                    mMessageView.stopRefresh();
            }
            @Override
            public void onResponse(String response, int id) {
                MyMessageBean mMessageBean = GsonUtil.toString(response, MyMessageBean.class);
                Log.i("xxx", mMessageBean.toString());

                if (mMessageBean.getCode().equals("200")) {
                    mMessageAdapter.getmDatas().setData(mMessageBean.getData());
                    mMessageAdapter.notifyDataSetChanged();
                }else if (mMessageBean.getCode().equals("499")){
                    mMessageView.startLoginActivity();
                    //ReLoginUtil.relogin();
                }
                mMessageView.stopRefresh();
            }
        });
    }

    @Override
    public void pullLoadMore(MyMessageAdapter mMessageAdapter) {
        int lastMessageId = mMessageAdapter.getmDatas().getData().get(mMessageAdapter.getmDatas().getData().size()-1).getMessageId();
        mMessageView.showToast("pullLoadMore lastMessageId:"+lastMessageId);
        mMessageModel.loadMyMessageData(
                SharedUtil.getString(App.instance,FieldConstant.token)
                , lastMessageId
                , 10
                , new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (mMessageView!=null)
                            mMessageView.stopRefresh();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        MyMessageBean mMessageBean = GsonUtil.toString(response, MyMessageBean.class);
                        Log.i("xxx", mMessageBean.toString());

                        if (mMessageBean.getCode().equals("200")) {
                            mMessageAdapter.getmDatas().getData().addAll(mMessageBean.getData());
                            mMessageAdapter.notifyDataSetChanged();
                        }else if (mMessageBean.getCode().equals("499")){
                            mMessageView.startLoginActivity();
                            //ReLoginUtil.relogin();
                        }
                        mMessageView.stopRefresh();
                    }

                });
    }

    @Override
    public void rejectMessage(MyMessageAdapter mMessageAdapter, int position, int messageId) {
        mMessageModel.agreeMessage(
                SharedUtil.getString(mContext, FieldConstant.token)
                ,messageId
                ,false
                ,mMessageAdapter.getmDatas().getData().get(position).getInvationId()
                ,mMessageAdapter.getmDatas().getData().get(position).getApplyUserId()
                , new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mMessageView!=null)
                mMessageView.showToast("网络不稳定");
            }

            @Override
            public void onResponse(String response, int id) {
                BaseEmptyBean baseEmptyBean= GsonUtil.toString(response, BaseEmptyBean.class);
                Log.i("response", "response:"+response.toString());
                Log.i("response", "messageresponsebeen:"+baseEmptyBean.toString());
                if (baseEmptyBean.getCode()==200){
                    mMessageAdapter.getmDatas().getData().remove(position);
                    mMessageAdapter.notifyItemRemoved(position);
                }else {
                    mMessageView.showToast(baseEmptyBean.getMsg());
                }
            }
        });
    }


}
