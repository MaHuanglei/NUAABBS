package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nuaabbs.action.LoginActivity;
import com.example.nuaabbs.action.RegisterActivity;
import com.example.nuaabbs.common.CommonRequest;
import com.example.nuaabbs.common.CommonResponse;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.LogUtil;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.UserInfo;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginTask extends AsyncTask<String, Void, Boolean> {

    Context context;
    ProgressBar progressBar = null;
    CommonResponse res;
    Gson gson = new Gson();

    public LoginTask(Context context, View v){
        this.context = context;
        progressBar = (ProgressBar) v;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            if(!MyApplication.isNetworkAvailable()){
                res.setResCode(Constant.RESCODE_NET_FAIL);
                publishProgress();
                return false;
            }
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setParam1(params[0]+":&:"+params[1]);
            String json = gson.toJson(commonRequest);

            /*OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();*/
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = FormBody.create(MediaType.parse(Constant.CONNECT_CHARSET), json);
            Request request = new Request.Builder()
                    .url(Constant.URL_Login)
                    .post(requestBody).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            LogUtil.d("LoginActivity", responseData);
            res = gson.fromJson(responseData, CommonResponse.class);
            publishProgress();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        String resCode = res.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            MyApplication.loginState = true;
            MyApplication.userInfo = gson.fromJson(res.getResParam(), UserInfo.class);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context,res.getResMsg(), Toast.LENGTH_SHORT).show();
            ((LoginActivity)context).loginSuccess();
        }else if(resCode.equals(Constant.RESCODE_PASSWORD_FALSE)) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(context, res.getResMsg(), Toast.LENGTH_SHORT).show();
        }else if(resCode.equals(Constant.RESCODE_NET_FAIL)){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(context, Constant.NET_UNABLE, Toast.LENGTH_SHORT).show();
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            LogUtil.e("LoginActivity", "Login error");
            Toast.makeText(context, Constant.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
