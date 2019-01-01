package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nuaabbs.action.LoginActivity;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.UserInfo;
import com.example.nuaabbs.util.OkHttpUtil;


public class LoginTask extends AsyncTask<String, Void, Boolean> {

    Context context;
    ProgressBar progressBar;
    CommonResponse commonResponse;

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
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setParam1(params[0]+":&:"+params[1]);

            OkHttpUtil.executeTask(commonRequest, Constant.URL_Login);
            commonResponse = OkHttpUtil.getCommonResponse();

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

        String resCode = commonResponse.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            commonResponse = OkHttpUtil.getCommonResponse();

            MyApplication.loginState = true;
            MyApplication.userInfo = Constant.gson
                    .fromJson(commonResponse.getResParam(), UserInfo.class);

            progressBar.setVisibility(View.GONE);
            Toast.makeText(context,commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
            ((LoginActivity)context).loginSuccess();
        }else if(resCode.equals(Constant.RESCODE_PASSWORD_FALSE)) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(context, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            OkHttpUtil.stdDealResult(context, "LoginActivity LoginTask");
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
