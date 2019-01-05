package com.example.nuaabbs.util;

import com.example.nuaabbs.object.Comment;
import java.util.List;

public class HelperUtil {

    public static boolean InsertComment(Comment newComment, List<Comment> commentList){

        int index = 0;
        boolean flag = false;

        int id = newComment.getFollowCommentID();
        for(Comment comment : commentList){
            if(!flag){
                if(comment.getCommentID() == id){
                    flag = true;
                }
            }else{
                if(comment.isDependent() && comment.getCommentID() != id)
                    break;
            }

            ++index;
        }

        if(flag) commentList.add(index, newComment);

        return flag;
    }
}
