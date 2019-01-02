package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nuaabbs.R;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";

    private Context mContext;

    private List<Post> postList;

    private Comment[] comments = {new Comment(), new Comment(), new Comment(), new Comment()
            ,new Comment(), new Comment()};
    List<Comment> commentList = new ArrayList<Comment>();

    public PostAdapter() {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View postView;
        RecyclerView commentView;

        CardView cardView;
        TextView sender;
        TextView time;
        CircleImageView headPortrait;
        TextView postInfo;
        ImageView views;
        TextView viewNum;
        ImageView thumb_up;
        TextView thumb_up_Num;
        ImageView comment;
        TextView commentNum;

        public ViewHolder(View view) {
            super(view);
            postView = view;
            commentView = view.findViewById(R.id.comment_RecyclerView);

            cardView = (CardView) view;
            sender = view.findViewById(R.id.post_sender);
            time = view.findViewById(R.id.post_time);
            headPortrait = view.findViewById(R.id.poster_head);
            postInfo = view.findViewById(R.id.post_info);
            views = view.findViewById(R.id.views);
            viewNum = view.findViewById(R.id.view_num);
            thumb_up = view.findViewById(R.id.thumb_up);
            thumb_up_Num = view.findViewById(R.id.thumb_up_num);
            comment = view.findViewById(R.id.comment);
            commentNum = view.findViewById(R.id.comment_num);

        }

    }

    public PostAdapter(Context context, List<Post> postList) {
        this.mContext = context;
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.postView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = postList.get(position);
            }
        });

        holder.thumb_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = postList.get(position);
                post.addThumb_upNum();
                post.calculateHotDegree();
                holder.thumb_up_Num.setText(Integer.toString(post.getThumb_upNum()));
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = postList.get(position);
                post.addCommentNum();
                post.calculateHotDegree();
                holder.commentNum.setText(Integer.toString(post.getCommentNum()));
            }
        });

        holder.postInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = postList.get(position);
                post.addViewNum();
                post.calculateHotDegree();
                holder.viewNum.setText(Integer.toString(post.getViewNum()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        Glide.with(mContext).load(R.drawable.plane).into(holder.headPortrait);
        holder.sender.setText(post.getPoster());
        holder.time.setText(post.getDateStr());
        holder.postInfo.setText(post.getPostContent());
        Glide.with(mContext).load(R.drawable.ic_menu_view).into(holder.views);
        holder.viewNum.setText(Integer.toString(post.getViewNum()));
        Glide.with(mContext).load(R.drawable.btn_thumb_up).into(holder.thumb_up);
        holder.thumb_up_Num.setText(Integer.toString(post.getThumb_upNum()));
        Glide.with(mContext).load(R.drawable.ic_menu_comment).into(holder.comment);
        holder.commentNum.setText(Integer.toString(post.getCommentNum()));

        initComment();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.commentView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(mContext, commentList);
        holder.commentView.setAdapter(commentAdapter);

    }

    private void initComment(){
        commentList.clear();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int index = random.nextInt(comments.length);
            this.commentList.add(comments[index]);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}
