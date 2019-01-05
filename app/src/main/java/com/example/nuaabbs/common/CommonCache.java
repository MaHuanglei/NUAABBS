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
}
