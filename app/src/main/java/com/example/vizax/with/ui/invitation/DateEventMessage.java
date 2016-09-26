package com.example.vizax.with.ui.invitation;

/**
 * Created by maozhilin on 16/9/18.
 */
public class DateEventMessage {
    String date;
    String invitation_date;
    public String getInvitation_date() {
        return invitation_date;
    }

    public void setInvitation_date(String invitation_date) {
        this.invitation_date = invitation_date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
       return date;
    }
}
