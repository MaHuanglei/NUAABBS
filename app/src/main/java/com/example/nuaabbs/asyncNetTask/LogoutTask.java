package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.nuaabbs.MainActivity;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.common.UserInfo;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.util.OkHttpUtil;
import com.google.gson.Gson;


public class LogoutTask extends AsyncTask<Void, Void, Boolean> {
    Context context;
    CommonResponse response;

    public LogoutTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            String param = "id=" + Integer.toString(MyApplication.userInfo.getId())
                    + ":&:" + "userName=" + MyApplication.userInfo.getUserName();
            CommonRequest commonRequest = new CommonRequest("", param, "");
            OkHttpUtil.executeTask(commonRequest, Constant.URL_Logout);
            response = OkHttpUtil.getCommonResponse();

            publishProgress();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        String resCode = response.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            MyApplication.userInfo = new Gson()
                    .fromJson(OkHttpUtil.getCommonResponse().getResParam(), UserInfo.class);
            MyApplication.loginState = false;
            PostListManager.myPostList.clear();
            ((MainActivity)context).RefreshNavigationView();
            Toast.makeText(context, "下线成功！", Toast.LENGTH_SHORT).show();
        }else OkHttpUtil.stdDealResult(context, "MainActivity Logout");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
