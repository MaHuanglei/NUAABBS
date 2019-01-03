package com.example.nuaabbs.object;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Comment implements Serializable {
    private int commentID;
    private int followPostID;
    private int commentUserID;
    private String commentUser;
    private String time;
    private boolean isDependent;
    private int followCommentID;
    private String commentInfo;

    public Comment(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = dateFormat.format(new Date(System.currentTimeMillis()));

        commentUser = "X";
        isDependent = true;
        commentInfo = "That sounds good!";
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getFollowPostID() {
        return followPostID;
    }

    public void setFollowPostID(int followPostID) {
        this.followPostID = followPostID;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public int getCommentUserID() {
        return commentUserID;
    }

    public void setCommentUserID(int commentUserID) {
        this.commentUserID = commentUserID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDependent() {
        return isDependent;
    }

    public void setDependent(boolean dependent) {
        isDependent = dependent;
    }

    public int getFollowCommentID() {
        return followCommentID;
    }

    public void setFollowCommentID(int followCommentID) {
        this.followCommentID = followCommentID;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }
}
