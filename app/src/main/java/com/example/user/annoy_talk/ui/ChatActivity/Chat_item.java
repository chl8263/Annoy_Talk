package com.example.user.annoy_talk.ui.ChatActivity;

/**
 * Created by choi on 2017-12-04.
 */

public class Chat_item {
    private String name;
    private String Content;
    private int viewType;

    public Chat_item(String name,String content, int viewType) {
        this.name =name;
        Content = content;
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
