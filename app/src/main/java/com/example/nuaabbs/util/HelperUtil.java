package com.example.nuaabbs.util;

import com.example.nuaabbs.R;
import com.example.nuaabbs.object.Comment;
import java.util.List;
import java.util.Random;

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

    public static int getRandomHeadID(){
        int[] IDs = {R.drawable.ic_head_1, R.drawable.ic_head_2, R.drawable.ic_head_3,
                R.drawable.ic_head_4, R.drawable.ic_head_5, R.drawable.ic_head_6,
                R.drawable.ic_head_7, R.drawable.ic_head_8, R.drawable.ic_head_9,
                R.drawable.ic_head_10};
        int index = new Random().nextInt(10);
        return IDs[index];
    }
}
