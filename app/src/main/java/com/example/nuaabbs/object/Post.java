package com.example.nuaabbs.object;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Post {
    int postId;
    String dateStr;
    String poster;
    String postContent;
    String label;

    int thumb_upNum = 0;
    int viewNum = 0;
    int commentNum = 0;
    int hotDegree;
    List<Comment> comments = null;

    public Post(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateStr = dateFormat.format(new Date(System.currentTimeMillis()));

        poster = "X";
        label = "life";
        postContent = "Write your story!";
        hotDegree = (thumb_upNum*4 + viewNum*2 + commentNum*4)/10;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getThumb_upNum() {
        return thumb_upNum;
    }

    public void setThumb_upNum(int thumb_upNum) {
        this.thumb_upNum = thumb_upNum;
    }

    public void addThumb_upNum(){
        this.thumb_upNum += 1;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public void addViewNum(){
        this.viewNum += 1;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void addCommentNum(){
        this.commentNum += 1;
    }

    public int getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(int hotDegree) {
        this.hotDegree = hotDegree;
    }

    public void calculateHotDegree(){
        this.hotDegree = (this.thumb_upNum*4 + this.viewNum*2 + this.commentNum*4)/10;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComments(Comment comment){
        this.comments.add(comment);
    }
}