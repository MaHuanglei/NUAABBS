package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.nuaabbs.MainActivity;
import com.example.nuaabbs.common.CommonRequest;
import com.example.nuaabbs.common.CommonResponse;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.LogUtil;
import com.example.nuaabbs.common.MyApplication;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogoutTask extends AsyncTask<Void, Boolean, Boolean> {
    Context context;
    CommonResponse res = new CommonResponse();

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
            if(!MyApplication.isNetworkAvailable()){
                res.setResCode(Constant.RESCODE_NET_FAIL);
                publishProgress(false);
                return false;
            }
            String param = "id=" + Integer.toString(MyApplication.userInfo.getId())
                    + ":&:" + "userName=" + MyApplication.userInfo.getUserName();
            CommonRequest commonRequest = new CommonRequest("", param, "");
            Gson gson = new Gson();
            String json = gson.toJson(commonRequest);

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = FormBody.create(MediaType.parse(Constant.CONNECT_CHARSET), json);
            Request request = new Request.Builder()
                    .url(Constant.URL_Logout)
                    .post(requestBody).build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            LogUtil.d("MainActivity", responseData);
            if(!responseData.equals("")){
                res = gson.fromJson(responseData, CommonResponse.class);
                publishProgress(true);
            }else{
                LogUtil.e("MainActivity", "logout error");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);

        String resCode = res.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            MyApplication.loginState = false;
            ((MainActivity)context).RefreshNavigationView();
            Toast.makeText(context, "下线成功！", Toast.LENGTH_SHORT).show();
        }else if(resCode.equals(Constant.RESCODE_NET_FAIL)){
            Toast.makeText(context, Constant.NET_UNABLE, Toast.LENGTH_SHORT).show();
        }else{
            LogUtil.e("Logout", "Logout error");
            Toast.makeText(context, Constant.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
