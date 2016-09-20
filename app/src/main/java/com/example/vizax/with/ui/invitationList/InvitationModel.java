package com.example.vizax.with.ui.invitationList;

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
public class InvitationModel implements InvitationContact.InvitationlModel {
    InvitationBaseBean result;
    private InvitationRecyclerViewAdapter mAdapter;
    private InvitationBaseBean  mData;
    private InvitationContact.InvitationCallBack invitationCallBack;
    @Override
    public void getData(String typeId, String userId, InvitationContact.InvitationCallBack after) {
        this.invitationCallBack = after;
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        invitationCallBack.setAdapter(result);
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
                        response = "{\"data\":[{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true}],\"msg\":\"\\u4fe1\\u606f\",\"code\":200}";
                        try {
                            baseBean = GsonUtil.toString(response, InvitationBaseBean.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("");
                        }
                        System.out.println("---"+ response);
                        if (baseBean.getCode().equals("200")){
                            System.out.println("success!!!");
                            result = baseBean;
                            System.out.println("result="+result.getData().get(0).toString());
                        }
                        else{
                            result = null;
                            System.out.println("null!!!");
                        }
                    }
                });

    }

    @Override
    public void addData(String finalItemId, String count,InvitationBaseBean invitationBaseBean) {
        mData = invitationBaseBean;
        OkHttpUtils.post()
                .url(APIConstant.getApi(APIConstant.INVITATION_GETINVITATIONS))
                .addParams("lastInvitationId",finalItemId)
                .addParams("limit",count)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("erro!!!");
                        e.printStackTrace();
                        result = null;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        InvitationBaseBean baseBean = null;

                        //response = "{\"data\":[{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true},{\"title\":\"\\u72fc\\u4eba\\u6740\",\"invitaionId\":1,\"currentNumber\":2,\"originatorNickname\":\"\\u963f\\u9053\\u514b\",\"iconUrl\":\"icon\\/icon1.png\",\"invitationTime\":\"2016-9-8 19:33\",\"originatorHeadUrl\":\"image\\/head.png\",\"place\":\"\\u4e2d\\u5df4\",\"content\":\"\\u6211\\u4eec\\u5c06\\u8fce\\u6765\\u672c\\u5b66\\u671f\\u4e00\\u573a\\u72fc\\u4eba\\u6740\\u6bd4\\u8d5b\",\"totalNumber\":16,\"publishTime\":\"2016-9-8 19:33\",\"members\":[{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"},{\"headUrl\":\"image\\/head.png\",\"realName\":\"\\u6f58\\u5927\\u7237\",\"phone\":1562261234,\"sex\":\"\\u7537\",\"userId\":\"1\"}],\"sexRequire\":\"\\u53ea\\u8981\\u5973\\u751f\",\"isJoin\":true}],\"msg\":\"\\u4fe1\\u606f\",\"code\":200}";
                        try {
                            baseBean = GsonUtil.toString(response, InvitationBaseBean.class);

                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("");
                        }
                        for(int i = 0;i<baseBean.getData().size();i++){
                            mData.getData().add(baseBean.getData().get(i));
                        }
                        System.out.println("更新后的数据="+mData.getData().toString());
                        if (baseBean.getCode().equals("200")){
                            System.out.println("success!!!");
                            result = baseBean;
                            System.out.println("result="+result.getData().get(0).toString());
                        }
                        else{
                            result = null;
                            System.out.println("null!!!");
                        }
                    }
                });

    }


}

