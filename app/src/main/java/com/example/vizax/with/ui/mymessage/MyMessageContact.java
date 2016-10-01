package com.example.vizax.with.ui.mymessage;

import com.example.vizax.with.base.BasePresenter;
import com.example.vizax.with.base.BaseView;
import com.example.vizax.with.bean.MyMessageBean;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

/**
 * Created by Xuhai on 2016/9/23.
 */

public interface MyMessageContact {
    interface View extends BaseView{
        public void loadDatas(List<MyMessageBean.DataBean> mMessageList, int lastId);
    }

    interface Modle{
        public void loadMyMessageData(String token, int lastMessageId, int limit, StringCallback stringCallback);
    }

    interface Presenter extends BasePresenter<View> {
        public void loadMessageData(String token, int lastMessageId, int limit);
    }
}
