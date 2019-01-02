package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuaabbs.R;
import com.example.nuaabbs.object.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> commentList;
    Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView commentUser;
        TextView commentInfo;

        public ViewHolder(View view){
            super(view);
            commentUser = (TextView)view.findViewById(R.id.comment_user);
            commentInfo = (TextView)view.findViewById(R.id.comment_info);
        }
    }

    public CommentAdapter(Context mContext, List<Comment> commentList){
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentUser.setText(comment.getCommentUser()+":");
        holder.commentInfo.setText(comment.getCommentInfo());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
