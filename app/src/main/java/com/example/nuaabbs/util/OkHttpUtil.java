package com.example.nuaabbs.util;

import android.content.Context;
import android.widget.Toast;

import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private static OkHttpClient client;
    private static CommonResponse commonResponse;

    public static void prepareOkHttp(){
        if(client != null) return;

        client = new OkHttpClient();
        client.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
    }

    public static void executeTask(CommonRequest commonRequest, String url){
        prepareOkHttp();

        try{
            String json = Constant.gson.toJson(commonRequest);

            RequestBody requestBody = FormBody.create(MediaType.parse(Constant.CONNECT_CHARSET), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody).build();
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String responseData = response.body().string();
                commonResponse = Constant.gson.fromJson(responseData, CommonResponse.class);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("OkHttpUtil", e.toString());

            if (e.getCause().equals(SocketTimeoutException.class)){
                commonResponse = new CommonResponse(Constant.NET_TIMEOUT,"","");
            }
        }
    }

    public static CommonResponse getCommonResponse(){
        return commonResponse;
    }

    public static void stdDealResult(Context context, String tag){

        String resCode = commonResponse.getResCode();
        if(resCode.equals(Constant.RESCODE_NET_FAIL)){
            Toast.makeText(context, Constant.NET_UNABLE, Toast.LENGTH_SHORT).show();
        }else if(resCode.equals(Constant.RESCODE_NET_TIMEOUT)){
            Toast.makeText(context, Constant.NET_TIMEOUT, Toast.LENGTH_SHORT).show();
        }else{
            LogUtil.e(tag + Constant.OKHTTP_TAG, Constant.SYSTEM_ERROR);
            Toast.makeText(context, Constant.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
        }
    }
    
}
