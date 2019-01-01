package com.example.nuaabbs.object;

public class Post {


    String sender;
    String postInfo;

    public Post(String sender, String info){
        this.sender = sender;
        this.postInfo = info;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }
}
