package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;


import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.asyncNetTask.RequestMyPostTask;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends BaseActivity {
    private List<Post> postList = new ArrayList<>();
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        initPosts();
        RecyclerView myPostRecyclerView = findViewById(R.id.my_post_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myPostRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(this, postList, true);
        myPostRecyclerView.setAdapter(adapter);
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
        RequestMyPostTask requestMyPostTask = new RequestMyPostTask(this);
        requestMyPostTask.execute(MyApplication.userInfo.getId()+"");
    }

    public void RequestSuccess(List<Post> newPostList){
        postList.addAll(newPostList);
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }
}
