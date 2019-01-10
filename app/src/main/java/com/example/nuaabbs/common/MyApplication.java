package com.example.nuaabbs.common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.util.LogUtil;

public class MyApplication extends Application {

    private static Context context;
    public static boolean loginState = false;
    public static UserInfo userInfo = new UserInfo();
    private static int screenHeight = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isNetworkAvailable(){
        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager == null) return false;

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable()) return false;

        return true;
    }

    public static void setScreenHeight(int height){
        screenHeight = height;
    }

    public static int getScreenHeight(){
        return screenHeight;
    }
}
