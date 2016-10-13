package com.example.vizax.with.EventBus;

/**
 * Created by Young on 2016/10/11.
 */
public class UserInfoMessage {
    private String url;

    public UserInfoMessage(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String name;
}



