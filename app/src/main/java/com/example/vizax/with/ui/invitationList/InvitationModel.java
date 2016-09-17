package com.example.vizax.with.ui.invitationList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vizax.with.adapter.InvitationRecyclerViewAdapter;
import com.example.vizax.with.bean.InvitationBaseBean;
import com.example.vizax.with.constant.APIConstant;
import com.example.vizax.with.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Young on 2016/9/16.
 */
public class InvitationModel implements InvitationModelImple {
    InvitationBaseBean result;
    private InvitationRecyclerViewAdapter mAdapter;
    private InvitationBaseBean  mData;
    @Override
    public void getData(Context context,RecyclerView recyclerView,int visible,String typeId,String userId) {
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        mAdapter = new InvitationRecyclerViewAdapter(context,result);
                        mAdapter.setExpend(visible);//设置时间右边的expend箭头是否显示 我发起的界面显示，其他界面不显示
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(mAdapter);
                        System.out.println("?????"+result);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("erro!!!");
                        e.printStackTrace();
                       result = null;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                       InvitationBaseBean baseBean = null;
                        try {
                            baseBean = GsonUtil.toString(response, InvitationBaseBean.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("");
                        }
                       // System.out.println("---"+ (baseBean.getData()[0].getOriginatorrealName()));
                        if (baseBean.getCode().equals("200")){
                            System.out.println("success!!!");
                            result = baseBean;
                            System.out.println("result="+result);
                        }
                        else{
                            result = null;
                            System.out.println("null!!!");
                        }
                    }
                });

    }


}
interface InvitationModelImple{
    void getData(Context context,RecyclerView recyclerView,int visible,String typeId,String userId);
}
