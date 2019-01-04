package com.example.nuaabbs.asyncNetTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.CommonRequest;
import com.example.nuaabbs.object.CommonResponse;
import com.example.nuaabbs.util.OkHttpUtil;

public class PostRelatedActionTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    PostAdapter adapter;
    private CommonResponse commonResponse;

    public PostRelatedActionTask(Context context, PostAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setRequestCode(params[0]);
            commonRequest.setParam1(params[1]);

            OkHttpUtil.executeTask(commonRequest, Constant.URL_PostRelatedAction);
            commonResponse = OkHttpUtil.getCommonResponse();

            publishProgress(params[0], params[1]);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        String resCode = commonResponse.getResCode();
        if(!resCode.equals(Constant.RESCODE_SUCCESS)){
            adapter.DealFail(values[0], values[1]);
            OkHttpUtil.stdDealResult(context, "PostRelatedAction");
        }
    }
}
