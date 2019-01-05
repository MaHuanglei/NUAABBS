package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.fragment.BaseFragment;
import com.example.nuaabbs.fragment.LifePostFragment;
import com.example.nuaabbs.fragment.StudyPostFragment;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.HelperUtil;
import com.example.nuaabbs.util.OkHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RequestLabelPostTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private CommonResponse commonResponse;
    private BaseFragment fragment;
    private String postLabel;

    public RequestLabelPostTask(Context context, BaseFragment fragment){
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setRequestCode(params[0]);
            postLabel = new String(params[0]);

            OkHttpUtil.executeTask(commonRequest, Constant.URL_RequestLabelPost);
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
            List<Post> newPostList = Constant.gson.fromJson(commonResponse.getResParam(),
                                            new TypeToken<List<Post>>(){}.getType());

            if(postLabel.equals(Constant.REQCODE_LIFE_Post)){
                PostListManager.refreshLifePostList(newPostList);
                ((LifePostFragment)fragment).RequestSuccess();
            } else{
                PostListManager.refreshStudyPostList(newPostList);
                ((StudyPostFragment) fragment).RequestSuccess();
            }
        }else {
            if(postLabel.equals(Constant.REQCODE_LIFE_Post))
                ((LifePostFragment)fragment).RequestFail();
            else ((StudyPostFragment)fragment).RequestFail();
            OkHttpUtil.stdDealResult(context, "MainActivity RefreshPostTask");
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
