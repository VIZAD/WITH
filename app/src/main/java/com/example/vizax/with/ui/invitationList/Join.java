package com.example.vizax.with.ui.invitationList;

import com.example.vizax.with.constant.APIConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class Join {
    public static changebtnsrc change;
    public static void join(String id,String type,changebtnsrc cs){

        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITAION_PARTICIPATEINVITATION))
                .addParams("invitationId",id)
                .addParams("type",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        change = cs;
                        change.setsrc();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("erro!!!");

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("success!!!");
                    }
                });
    }
    public interface changebtnsrc{
        void setsrc();
    }
}
