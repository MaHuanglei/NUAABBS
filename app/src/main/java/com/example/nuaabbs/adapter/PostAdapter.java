package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuaabbs.R;
import com.example.nuaabbs.object.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";

    private Context mContext;

    private List<Post> postList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView sender;
        TextView postInfo;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            sender = view.findViewById(R.id.post_sender);
            postInfo = view.findViewById(R.id.post_info);
        }
    }

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.sender.setText(post.getSender());
        holder.postInfo.setText(post.getPostInfo());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}
