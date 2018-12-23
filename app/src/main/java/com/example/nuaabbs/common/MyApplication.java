package com.example.nuaabbs.common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application {

    private static Context context;
    public static boolean loginState = false;
    public static UserInfo userInfo = new UserInfo();


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
}
