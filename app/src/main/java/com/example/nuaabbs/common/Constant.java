package com.example.nuaabbs.common;

import com.google.gson.Gson;

public class Constant {

    /* Some URL for connection with server */
    public static String URL = "http://118.25.131.221:8080/BBSSever/";
    public static String URL_Login = URL + "login";
    public static String URL_Register = URL + "register";
    public static String URL_Logout = URL + "logout";
    public static String URL_UpdateUserDetail = URL + "updateUserDetail";
    public static String URL_CreateNewPost = URL + "createNewPost";
    public static String URL_RequestLabelPost = URL + "requestLabelPost";
    public static String URL_RequestMyPost = URL + "requestMyPost";
    public static String URL_RequestHotPost = URL + "requestHotPost";

    // charset of connection with server
    public static String CONNECT_CHARSET = "application/json; charset=utf-8";

    public static String REQCODE_LIFE_Post = "生活";
    public static String REQCODE_STUDY_Post = "学习";

    // constant string for response code
    public static String RESCODE_SUCCESS = "100";
    public static String RESCODE_PASSWORD_FALSE = "200";
    public static String RESCODE_REGISTERED = "201";
    public static String RESCODE_SQLEXCEPTION = "202";
    public static String RESCODE_NET_FAIL = "400";
    public static String RESCODE_SYSTEM_ERROR = "403";
    public static String RESCODE_NET_TIMEOUT = "500";


    public static String REGISTERED = "该用户名已存在";
    public static String SYSTEM_ERROR = "system error";
    public static String NET_UNABLE = "网络不可用！";
    public static String NET_TIMEOUT = "连接超时！";

    public static final int TAKE_PHOTO = 1024;
    public static final int CHOOSE_PHOTO = 1025;

    // 自定义日志标题
    public static String APP_LOG_TAG = "NUAA-BBS";
    public static String OKHTTP_TAG = " OkHttp";

    // default date
    public static long dataDefault = 946703833254l;

    public static Gson gson = new Gson();
}
