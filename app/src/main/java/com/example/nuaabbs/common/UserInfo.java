package com.example.nuaabbs.common;

import java.sql.Date;

public class UserInfo {
    int id;
    String userName;
    String password;
    String schoolID;
    String headPortrait;
    String  sex;
    Date birthday;
    String idioGraph;
    int postNum;
    int draftNum;
    int collectPostNum;
    int careUserNum;
    int fansNum;

    public void PrintUserInfo(){

        LogUtil.d("UserInfo", "id = " + Integer.toString(this.id));
        LogUtil.d("UserInfo", "userName = " + this.userName);
        LogUtil.d("UserInfo", "password = " + this.password);

        if(this.schoolID == null) this.schoolID = "null";
        LogUtil.d("UserInfo", "schoolID = " + this.schoolID);

        LogUtil.d("UserInfo", "headPortrait = " + this.headPortrait);
        LogUtil.d("UserInfo", "sex = " + this.sex);

        if(this.birthday == null)
            LogUtil.d("UserInfo", "birthday = null");
        else LogUtil.d("UserInfo", "birthday = " + this.birthday.toString());
        LogUtil.d("UserInfo", "idiograph = " + this.idioGraph);
        LogUtil.d("UserInfo", "postNum = " + Integer.toString(this.postNum));
        LogUtil.d("UserInfo", "draftNum = " + Integer.toString(this.draftNum));
        LogUtil.d("UserInfo", "collectNum = " + Integer.toString(this.collectPostNum));
        LogUtil.d("UserInfo", "careUserNum = " + Integer.toString(this.careUserNum));
        LogUtil.d("UserInfo", "fansNum = " + Integer.toString(this.fansNum));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdioGraph() {
        return idioGraph;
    }

    public void setIdioGraph(String idioGraph) {
        this.idioGraph = idioGraph;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public int getDraftNum() {
        return draftNum;
    }

    public void setDraftNum(int draftNum) {
        this.draftNum = draftNum;
    }

    public int getCollectPostNum() {
        return collectPostNum;
    }

    public void setCollectPostNum(int collectPostNum) {
        this.collectPostNum = collectPostNum;
    }

    public int getCareUserNum() {
        return careUserNum;
    }

    public void setCareUserNum(int careUserNum) {
        this.careUserNum = careUserNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}
