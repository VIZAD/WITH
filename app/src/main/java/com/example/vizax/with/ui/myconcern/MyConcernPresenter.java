package com.example.vizax.with.ui.myconcern;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.constant.FieldConstant;
import com.example.vizax.with.ui.login.MainActivity;
import com.example.vizax.with.util.GsonUtil;
import com.example.vizax.with.util.SharedUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.example.vizax.with.constant.APIConstant.INVITATION_CONCERNUSER;
import static com.example.vizax.with.constant.APIConstant.INVITATION_GETCONCERNEDUSERS;

/**
 * Created by VIZAX on 2016/09/21.
 */

public class MyConcernPresenter implements MyConcernContact.Presenter  {
    private  MyConcernContact.View concernedUserFragmentView;
    private Context context;

    public MyConcernPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void IsCocern() {

        OkHttpUtils.post()
                .url(APIConstant.getApi(INVITATION_CONCERNUSER))
                .addParams("token", SharedUtil.getString(context,"token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context,e+"",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        IsConcern isConcern = GsonUtil.toListString(response,IsConcern.class);
                        Toast.makeText(context,isConcern.getMsg().toString(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onRefresh() {
        OkHttpUtils.post()
                .url(APIConstant.getApi(INVITATION_GETCONCERNEDUSERS))
                .addParams("token",SharedUtil.getString(context, FieldConstant.token))
                .addParams("concernedUserId","0")//最后一个id，刷新的话，则为0
                .addParams("limit","20")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        concernedUserFragmentView.showErrorToast(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("myresponse",response);
                        MyConcern myConcern = GsonUtil.toListString(response,MyConcern.class);
                        if (myConcern.getCode() != 200){
                            concernedUserFragmentView.showErrorToast(myConcern.getMsg());
                            if (myConcern.getCode() == 499){
                                SharedUtil.putBoolean(context,FieldConstant.ishadlogin,false);
                               concernedUserFragmentView.startLoginActivity();//登录
                            }
                            return;
                        }
                        Log.i("myresponse bean",myConcern.toString());
                        concernedUserFragmentView.setNewData(myConcern.getData());

                    }

                });
    }

    @Override
    public void onloadMore(int lastConcernedUserId ) {

        OkHttpUtils.post()
                .url(APIConstant.getApi(INVITATION_GETCONCERNEDUSERS))
                .addParams("token",SharedUtil.getString(context,FieldConstant.token))
                .addParams("concernedUserId",lastConcernedUserId+"")//最后一个id，刷新的话，则为0
                .addParams("limit","20")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        concernedUserFragmentView.showErrorToast(e.toString());
                        concernedUserFragmentView.stopRefresh();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("myresponse",response);
                        //mDatas = new ArrayList<MyConcern.DataBean>();
                        MyConcern myConcern = GsonUtil.toListString(response,MyConcern.class);
                        if (myConcern.getCode() != 200){
                            concernedUserFragmentView.showErrorToast(myConcern.getMsg());
                            return;
                        }
                        Log.i("myresponse bean",myConcern.toString());
                        concernedUserFragmentView.addData(myConcern.getData());
                        concernedUserFragmentView.stopRefresh();
                    }
                });
    }

    @Override
    public void attachView(@NonNull MyConcernContact.View View) {
        concernedUserFragmentView = View;
    }

    @Override
    public void detachView() {
        concernedUserFragmentView = null;
    }

}
