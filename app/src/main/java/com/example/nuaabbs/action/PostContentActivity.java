package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.CommentAdapter;
import com.example.nuaabbs.object.Post;


import de.hdodenhof.circleimageview.CircleImageView;

public class PostContentActivity extends BaseActivity {

    Post post;

    TextView sender;
    TextView time;
    TextView label;
    CircleImageView headPortrait;
    TextView postInfo;
    ImageView views;
    TextView viewNum;
    ImageView thumb_up;
    TextView thumb_up_Num;
    ImageView comment;
    TextView commentNum;

    RecyclerView commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        post = (Post)getIntent().getSerializableExtra("post_data");

        sender = findViewById(R.id.post_content_sender);
        time = findViewById(R.id.post_content_time);
        label = findViewById(R.id.post_content_label);
        headPortrait = findViewById(R.id.post_content_head);
        postInfo = findViewById(R.id.post_content_info);
        views = findViewById(R.id.post_content_views);
        viewNum = findViewById(R.id.post_content_view_num);
        thumb_up = findViewById(R.id.post_content_thumb_up);
        thumb_up_Num = findViewById(R.id.post_content_thumb_up_num);
        comment = findViewById(R.id.post_content_comment);
        commentNum = findViewById(R.id.post_content_comment_num);
        commentView = findViewById(R.id.post_content_comment_RecyclerView);

        sender.setText(post.getPoster());
        time.setText(post.getDateStr());
        label.setText(post.getLabel());
        Glide.with(this).load(R.drawable.plane).into(headPortrait);
        postInfo.setText(post.getPostContent());
        Glide.with(this).load(R.drawable.ic_menu_view).into(views);
        viewNum.setText(Integer.toString(post.getViewNum()));
        Glide.with(this).load(R.drawable.btn_thumb_up).into(thumb_up);
        thumb_up_Num.setText(Integer.toString(post.getThumb_upNum()));
        Glide.with(this).load(R.drawable.ic_menu_comment).into(comment);
        commentNum.setText(Integer.toString(post.getCommentNum()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(this, post.getComments());
        commentView.setAdapter(commentAdapter);
    }

    public static void actionStart(Context context, Post post){
        Intent intent = new Intent(context, PostContentActivity.class);
        intent.putExtra("post_data", post);
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

}
