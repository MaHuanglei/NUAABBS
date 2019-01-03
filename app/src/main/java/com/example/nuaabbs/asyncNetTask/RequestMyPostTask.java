package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.action.MyPostActivity;
import com.example.nuaabbs.action.PersonalPageActivity;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.OkHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RequestMyPostTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private CommonResponse commonResponse;

    public RequestMyPostTask(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setRequestCode(params[0]);

            OkHttpUtil.executeTask(commonRequest, Constant.URL_RequestMyPost);
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
            List<Post> newPostList = Constant.gson.fromJson(commonResponse.getResParam(),
                    new TypeToken<List<Post>>(){}.getType());
            if(((BaseActivity)context).getClass().getSimpleName().equals("MyPostActivity"))
                ((MyPostActivity)context).RequestSuccess(newPostList);
            else ((PersonalPageActivity)context).RequestSuccess(newPostList);
        }else {
            OkHttpUtil.stdDealResult(context, "RequestMyPost");
        }
    }
}
