package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nuaabbs.action.CreatePostActivity;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.util.OkHttpUtil;

public class CreatePostTask extends AsyncTask<String, Void, Boolean> {
    Context context;
    ProgressBar progressBar;
    CommonResponse commonResponse;
    OkHttpUtil okHttpUtil = OkHttpUtil.GetOkHttpUtil();

    public CreatePostTask(Context context, ProgressBar proBar){
        this.context = context;
        this.progressBar = proBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try{
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setParam1(strings[0]);

            commonResponse = okHttpUtil.executeTask(commonRequest, Constant.URL_CreateNewPost);

            publishProgress();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        String resCode = commonResponse.getResCode();
        if(resCode.equals(Constant.RESCODE_SUCCESS)){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
            ((CreatePostActivity)context).CreateSuccess(Integer.parseInt(commonResponse.getResParam()));
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            okHttpUtil.stdDealResult("CreateNewPostTask");
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
