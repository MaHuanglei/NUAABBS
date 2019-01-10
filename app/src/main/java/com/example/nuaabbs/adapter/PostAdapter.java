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
import com.example.nuaabbs.util.LogUtil;
import com.example.nuaabbs.util.PopUpEditWindow;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";
    private Context mContext;
    private List<Post> postList;
    private boolean showLabel;

    private PopUpEditWindow window;

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

        TextView addComment;
        View callbackView;

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

            addComment = view.findViewById(R.id.add_comment);
            callbackView = view.findViewById(R.id.add_comment_callback_view);
        }

        public void updateCommentNum(int num){
            commentNum.setText(""+num);
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
                PostContentActivity.actionStart(mContext, post);

                DealPostRelatedAction(Constant.REQCODE_VIEW, post.getPostID()+"");
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
                holder.addComment.performClick();
            }
        });

        holder.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyApplication.loginState){
                    Toast.makeText(mContext, "你还没有登录，无法评论哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                LogUtil.d("add comment view", "location top = "+location[1]);

                if(CommonCache.CurrentActivity.getActivityNum() == Constant.MainActivityNum){
                    CommonCache.AdjustHeight.setAdjustParam(true, location[1]-10);
                }

                ShowAddCommentWindow(holder.callbackView);
            }
        });

        holder.callbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = postList.get(holder.getAdapterPosition());

                Comment comment = CommonCache.NewComment.getNewComment();
                comment.setFollowPostID(post.getPostID());
                comment.setCommentInfo(window.getInputContent());

                post.addCommentNum();
                post.addComments(comment);
                post.calculateHotDegree();
                holder.updateCommentNum(post.getCommentNum());
                holder.commentView.getAdapter().notifyDataSetChanged();

                String param = Constant.gson.toJson(comment);
                DealPostRelatedAction(Constant.REQCODE_COMMENT, param);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);

        //Glide.with(mContext).load(HelperUtil.getRandomHeadID()).into(holder.headPortrait);
        holder.sender.setText(post.getPoster());
        holder.time.setText(post.getDateStr());
        if(showLabel) holder.label.setText(post.getLabel());
        holder.postInfo.setText(post.getPostContent());

        holder.viewNum.setText(post.getViewNum()+"");
        holder.thumb_up_Num.setText(post.getThumb_upNum()+"");
        holder.commentNum.setText(post.getCommentNum()+"");

        if(post.getComments() == null)
            post.setComments(new ArrayList<Comment>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.commentView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(mContext, post, holder);
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

    private void ShowAddCommentWindow(View callbackView){
        final View view = LayoutInflater.from(mContext).inflate(R.layout.comment_popupwindow, null);
        window = new PopUpEditWindow(mContext, view, callbackView,
                "说点什么吧...");
        window.show();
    }
}
