package com.example.nuaabbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.PostRelatedActionTask;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.HelperUtil;
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.PopUpEditWindow;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> commentList;
    private Context mContext;
    private int postID;

    PopUpEditWindow window;
    private int clickedCommentID = 0;
    private int belongCommentID = 0;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;
        TextView callbackView;

        TextView commentUser;
        TextView commentInfo;
        TextView responseLabel;
        TextView sendToUser;

        TextView commentInfoPlus;

        public ViewHolder(View view){
            super(view);
            commentView = view;
            callbackView = view.findViewById(R.id.callback_view);

            commentUser = view.findViewById(R.id.comment_user);
            commentInfo = view.findViewById(R.id.comment_info);
            responseLabel = view.findViewById(R.id.response_label);
            sendToUser = view.findViewById(R.id.send_to_user);

            commentInfoPlus = view.findViewById(R.id.comment_info_plus);
        }
    }

    public CommentAdapter(Context mContext, List<Comment> commentList, int postID){
        this.mContext = mContext;
        this.commentList = commentList;
        this.postID = postID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.commentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!MyApplication.loginState){
                    Toast.makeText(mContext, "你还没有登录，无法评论哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                Comment comment = commentList.get(holder.getAdapterPosition());
                clickedCommentID = comment.getCommentID();
                belongCommentID = comment.getBelongCommentID();

                window = new PopUpEditWindow(mContext, holder.callbackView,
                        "回复 " + comment.getCommentUser(), holder.commentView);
                window.show();
            }
        });

        holder.commentUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了 评论人", Toast.LENGTH_SHORT).show();
            }
        });

        holder.sendToUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了 被评论人", Toast.LENGTH_SHORT).show();
            }
        });

        holder.callbackView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Comment replyComment = CommonCache.getNewComment();
                replyComment.setFollowPostID(postID);
                replyComment.setCommentInfo(window.getInputContent());
                replyComment.setDependent(false);
                replyComment.setFollowCommentID(clickedCommentID);
                replyComment.setBelongCommentID(belongCommentID);

                HelperUtil.InsertComment(replyComment, commentList);
                CommentAdapter.this.notifyDataSetChanged();
                String param = Constant.gson.toJson(replyComment);

                PostRelatedActionTask task = new PostRelatedActionTask(mContext);
                task.execute(Constant.REQCODE_COMMENT, param);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comment comment = commentList.get(position);
        if(comment.isDependent()){
            holder.commentUser.setText(comment.getCommentUser() + " : ");
        }else{
            holder.commentUser.setText(comment.getCommentUser());
            holder.responseLabel.setVisibility(View.VISIBLE);
            holder.sendToUser.setVisibility(View.VISIBLE);
            holder.sendToUser.setText(GetFollowCommentUser(comment.getFollowCommentID()) + " : ");
        }

        final String info = comment.getCommentInfo();
        holder.commentInfo.setText(info);

        /*以下处理过程意在：当评论内容一行显示不完时，获取被省略的评论内容并显示在第二评论显示区，以获得更好的显示效果*/
        final TextView commentInfo = holder.commentInfo;
        final TextView commentInfoPlus = holder.commentInfoPlus;
        commentInfo.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("getEllipsisCount","start");
                int ellipsisCount = commentInfo.getLayout().getEllipsisCount(0);
                if(ellipsisCount > 0){
                    LogUtil.d("ellipsisCount = ", ""+ellipsisCount);
                    int ellipsisIndex = info.length()-ellipsisCount+1;
                    commentInfo.setText(info.substring(0, ellipsisIndex));
                    commentInfoPlus.setText(info.substring(ellipsisIndex));
                    commentInfo.setVisibility(View.VISIBLE);
                    commentInfoPlus.setVisibility(View.VISIBLE);
                }else{
                    commentInfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private String GetFollowCommentUser(int followCommentID){
        for (Comment comment: commentList) {
            if(comment.getCommentID() == followCommentID)
                return comment.getCommentUser();
        }
        return "";
    }
}
