package com.example.nuaabbs.object;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Comment {
    int commentID;
    int followPostID;
    String commentUser;
    String time;
    boolean isDependent;
    int followCommentID;
    String commentInfo;

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
