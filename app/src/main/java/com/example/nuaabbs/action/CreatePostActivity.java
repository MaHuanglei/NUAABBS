package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.CreatePostTask;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.LogUtil;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CreatePostActivity extends BaseActivity implements View.OnClickListener{
    EditText postContent;
    Post newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        postContent = findViewById(R.id.create_post_content);
        findViewById(R.id.action_create).setOnClickListener(this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, CreatePostActivity.class);
        context.startActivity(intent);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_create:
                if(TextUtils.isEmpty(postContent.getText().toString())){
                    Toast.makeText(this, "帖子内容不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                CreatePost();
                break;
            default:
        }
    }

    private void CreatePost(){
        newPost = new Post(true);
        String param = MyApplication.userInfo.getUserName() + ":&:" + MyApplication.userInfo.getId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tmp = dateFormat.format(new Date(System.currentTimeMillis()));
        newPost.setDateStr(tmp);
        param += ":&:" + tmp;

        int labelID = ((RadioGroup)findViewById(R.id.create_post_label)).getCheckedRadioButtonId();
        if(labelID == R.id.label_life){
            param += ":&:" + "生活";
            newPost.setLabel("生活");
        }
        else{
            param += ":&:" + "学习";
            newPost.setLabel("学习");
        }

        tmp = postContent.getText().toString();
        newPost.setPostContent(tmp);
        param += ":&:" + tmp;
        LogUtil.d("createNewPost", param);

        CreatePostTask createPostTask =
                new CreatePostTask(this, (ProgressBar)findViewById(R.id.create_post_bar));
        createPostTask.execute(param);
    }

    public void CreateSuccess(int postID){

        newPost.setPostID(postID);
        if(newPost.getLabel().equals("学习"))
            PostListManager.studyPostList.add(0, newPost);
        else PostListManager.lifePostList.add(0, newPost);
        PostListManager.myPostList.add(0, newPost);
        PostListManager.hotPostList.add(newPost);

        this.finish();
    }
}
