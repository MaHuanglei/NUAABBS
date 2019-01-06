package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nuaabbs.R;
import com.example.nuaabbs.action.PostContentActivity;
import com.example.nuaabbs.asyncNetTask.PostRelatedActionTask;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.object.Post;
import com.example.nuaabbs.util.HelperUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";
    private Context mContext;
    private List<Post> postList;
    private List<Comment> commentList;
    private boolean showLabel;

    public PostAdapter() {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View postView;

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

        EditText commentEdit;
        Button sendComment;

        public ViewHolder(View view) {
            super(view);
            postView = view;
            commentView = view.findViewById(R.id.comment_list);

            headPortrait = view.findViewById(R.id.post_head);
            sender = view.findViewById(R.id.post_sender);
            time = view.findViewById(R.id.post_time);
            label = view.findViewById(R.id.post_label);
            postInfo = view.findViewById(R.id.post_info);

            views = view.findViewById(R.id.views);
            viewNum = view.findViewById(R.id.view_num);
            thumb_up = view.findViewById(R.id.thumb_up);
            thumb_up_Num = view.findViewById(R.id.thumb_up_num);
            comment = view.findViewById(R.id.comment);
            commentNum = view.findViewById(R.id.comment_num);

            commentEdit = view.findViewById(R.id.comment_edit);
            sendComment = view.findViewById(R.id.send_comment);
        }
    }

    public PostAdapter(Context context, List<Post> postList, boolean showLabel) {
        this.mContext = context;
        this.postList = postList;
        this.showLabel = showLabel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        holder.postView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "you clicked PostView", Toast.LENGTH_SHORT).show();
            }
        });

        holder.sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.postInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Post post = postList.get(position);
                post.addViewNum();
                post.calculateHotDegree();
                holder.viewNum.setText(Integer.toString(post.getViewNum()));
                DealPostRelatedAction(Constant.REQCODE_VIEW, post.getPostID()+"");

                PostContentActivity.actionStart(mContext, post);
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
                DealPostRelatedAction(Constant.REQCODE_THUMBUP, post.getPostID()+"");
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.commentEdit.requestFocus();
                InputMethodManager imm = (InputMethodManager)
                        mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.commentEdit,0);
            }
        });

        holder.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.loginState){
                    String commentInfo = holder.commentEdit.getText().toString();
                    if(!commentInfo.isEmpty()){
                        int position = holder.getAdapterPosition();
                        Post post = postList.get(position);
                        post.addCommentNum();
                        post.calculateHotDegree();
                        holder.commentNum.setText(Integer.toString(post.getCommentNum()));

                        Comment comment = CommonCache.getNewComment();
                        comment.setFollowPostID(post.getPostID());
                        comment.setCommentInfo(commentInfo);

                        post.addComments(comment);
                        holder.commentView.getAdapter().notifyDataSetChanged();
                        holder.commentEdit.getText().clear();

                        String json = Constant.gson.toJson(comment);
                        DealPostRelatedAction(Constant.REQCODE_COMMENT, json);
                    }
                }else{
                    Toast.makeText(mContext, "您还未登录！", Toast.LENGTH_LONG).show();
                    holder.commentEdit.getText().clear();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);

        Glide.with(mContext).load(HelperUtil.getRandomHeadID()).into(holder.headPortrait);
        holder.sender.setText(post.getPoster());
        holder.time.setText(post.getDateStr());
        if(showLabel) holder.label.setText(post.getLabel());
        holder.postInfo.setText(post.getPostContent());

        holder.viewNum.setText(post.getViewNum()+"");
        holder.thumb_up_Num.setText(post.getThumb_upNum()+"");
        holder.commentNum.setText(post.getCommentNum()+"");

        commentList = post.getComments();
        if(commentList == null)
            commentList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.commentView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(mContext, commentList, post.getPostID());
        holder.commentView.setAdapter(commentAdapter);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private void DealPostRelatedAction(String reqCode, String param){
        PostRelatedActionTask task = new PostRelatedActionTask(mContext);
        task.execute(reqCode, param);
    }

    public void DealFail(String reqCode, String param){

    }
}
