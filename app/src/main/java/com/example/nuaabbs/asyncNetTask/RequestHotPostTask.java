package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.fragment.BaseFragment;
import com.example.nuaabbs.fragment.HotPostFragment;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.HelperUtil;
import com.example.nuaabbs.util.OkHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RequestHotPostTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private BaseFragment fragment;
    private CommonResponse commonResponse;

    public RequestHotPostTask(Context context, BaseFragment fragment){
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try{
            CommonRequest commonRequest = new CommonRequest();

            OkHttpUtil.executeTask(commonRequest, Constant.URL_RequestHotPost);
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
            PostListManager.refreshHotPostList(newPostList);

            ((HotPostFragment)fragment).RequestSuccess();
        }else {
            ((HotPostFragment)fragment).RequestFail();
            OkHttpUtil.stdDealResult(context, "MainActivity RequestHotPost");
        }
    }
}
