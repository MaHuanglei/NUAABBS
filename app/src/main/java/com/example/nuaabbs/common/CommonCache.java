package com.example.nuaabbs.common;

import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.LogUtil;

public class CommonCache {
    private static Comment newComment;

    public static Comment getNewComment(){
        newComment = new Comment();
        return newComment;
    }

    public static void setNewCommentID(int id){
        newComment.setCommentID(id);
        LogUtil.d("newComment.id = ", ""+id);
        LogUtil.d("newComment.info = ", newComment.getCommentInfo());
    }

    public static class CurrentActivity{
        static int activityNum = 0;
        /*
        0 : null
        1 : MainActivity
        2 : LoginActivity
        3 : RegisterActivity
        4 : MyPostActivity
        5 : MyDraftActivity
        6 : MyCareActivity
        7 : MyCollectActivity
        8 : PersonalPageActivity
        9 : UserDetailActivity
        10: ChangeUserDetailActivity
        11: PostContentActivity
        12: CreatePostActivity
        13: SearchActivity
        14: SystemSettingActivity
        15:
        16:
        17:
        18:
         */

        public static void setActivityNum(int num){
            activityNum = num;
        }

        public static int getActivityNum(){
            return activityNum;
        }
    }
}
