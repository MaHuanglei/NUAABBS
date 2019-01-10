package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.asyncNetTask.RequestMyPostTask;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.object.Post;

import java.util.List;

public class MyPostActivity extends BaseActivity {
    private List<Post> postList = PostListManager.myPostList;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        initPosts();

        swipeRefresh = findViewById(R.id.my_post_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPost();
            }
        });

        RecyclerView myPostRecyclerView = findViewById(R.id.my_post_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myPostRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(this, postList, true);
        myPostRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setActivityNum(Constant.MyPostActivityNum);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return true;
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, MyPostActivity.class);
        context.startActivity(intent);
    }

    private void initPosts(){
        if(PostListManager.myPostList.isEmpty()){
            refreshPost();
        }
    }

    private void refreshPost(){
        RequestMyPostTask requestMyPostTask = new RequestMyPostTask(this);
        requestMyPostTask.execute(MyApplication.userInfo.getId()+"");
    }

    public void RequestSuccess(){
        closeRefreshBar(true);

        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void closeRefreshBar(boolean showToast){
        if(swipeRefresh != null && swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
            if(showToast)
                Toast.makeText(this, "更新成功!", Toast.LENGTH_SHORT).show();
        }
    }
}
