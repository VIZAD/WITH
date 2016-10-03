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

        void showToast(String msg);
    }

    interface Modle{
        public void loadMyMessageData(String token, int lastMessageId, int limit, StringCallback stringCallback);

        void deleteMessage(String token,int messageId, StringCallback stringCallback);
        void readMessage(String token, int messageId, StringCallback stringCallback);

        void agreeMessage(String token, int messageId,boolean isAccept,int invitationId,int applyUserid, StringCallback stringCallback);

    }

    interface Presenter extends BasePresenter<View> {
        public void loadMessageData(String token, int lastMessageId, int limit);

        void deleteMessage(MyMessageAdapter mMessageAdapter, int adapterPosition, int messageId);
        void readMessage(MyMessageAdapter mMessageAdapter, int adapterPosition, int messageId);
        void rejectMessage(MyMessageAdapter mMessageAdapter, int position, int messageId);
        void agreeMessage(MyMessageAdapter mMessageAdapter, int position, int messageId);
    }
}
