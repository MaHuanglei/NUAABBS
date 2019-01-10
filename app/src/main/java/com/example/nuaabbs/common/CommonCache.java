package com.example.nuaabbs.common;

import com.example.nuaabbs.object.Comment;
import com.example.nuaabbs.util.LogUtil;

public class CommonCache {

    public static class NewComment{
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
    }


    public static class CurrentActivity{
        static int activityNum = 0;
        static int fragmentNum = 0;


        public static void setActivityNum(int num){
            activityNum = num;
        }

        public static int getActivityNum(){
            return activityNum;
        }

        public static int getFragmentNum() {
            LogUtil.d("get fragment num", "num = "+fragmentNum);
            return fragmentNum;
        }

        public static void setFragmentNum(int num) {
            fragmentNum = num;
            LogUtil.d("set fragment num", "num = "+num);
        }
    }

    public static class AdjustHeight{
        private static boolean isNeedAdjust = false;
        private static int adjustHeight = 0;

        public static boolean isIsNeedAdjust() {
            return isNeedAdjust;
        }

        public static void setIsNeedAdjust(boolean need) {
            isNeedAdjust = need;
        }

        public static int getAdjustHeight() {
            return adjustHeight;
        }

        public static void setAdjustHeight(int adjustHeight) {
            AdjustHeight.adjustHeight = adjustHeight;
        }

        public static void setAdjustParam(boolean need, int height){
            isNeedAdjust = need;
            adjustHeight = height;
        }
    }
}
