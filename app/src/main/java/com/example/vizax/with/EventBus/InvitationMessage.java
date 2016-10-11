package com.example.vizax.with.EventBus;

import com.example.vizax.with.bean.InvitationBean;

/**
 * Created by Administrator on 2016/10/11.
 */

public class InvitationMessage {

    private int position;
    private InvitationBean invitationBean;

    public InvitationMessage(int position, InvitationBean invitationBean) {
        this.position = position;
        this.invitationBean = invitationBean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public InvitationBean getInvitationBean() {
        return invitationBean;
    }

    public void setInvitationBean(InvitationBean invitationBean) {
        this.invitationBean = invitationBean;
    }
}
