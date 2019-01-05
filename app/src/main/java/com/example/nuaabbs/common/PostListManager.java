package com.example.nuaabbs.common;

import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;

public class PostListManager {

    public static List<Post> myPostList = new ArrayList<>();
    public static List<Post> hotPostList = new ArrayList<>();
    public static List<Post> studyPostList = new ArrayList<>();
    public static List<Post> lifePostList = new ArrayList<>();

    public PostListManager(){

    }

    public static void refreshMyPostList(List<Post> newPostList){
        refreshPostList(newPostList, myPostList);
    }

    public static void refreshHotPostList(List<Post> newPostList){
        refreshPostList(newPostList, hotPostList);
    }

    public static void refreshLifePostList(List<Post> newPostList){
        refreshPostList(newPostList, lifePostList);
    }

    public static void refreshStudyPostList(List<Post> newPostList){
        refreshPostList(newPostList, studyPostList);
    }

    public static void refreshPostList(List<Post> newPostList, List<Post> oldPostList){
        int curIndex = 0;
        int postIndex;
        for(Post newPost : newPostList){
            postIndex = findPost(newPost, oldPostList);
            if(postIndex != -1){
                oldPostList.remove(postIndex);
                oldPostList.add(postIndex, newPost);
            }else{
                oldPostList.add(curIndex, newPost);
                ++curIndex;
            }
        }
    }

    public static int findPost(Post newPost, List<Post> postList){
        int index = 0;
        int id = newPost.getPostID();
        for(Post post:postList){
            if(post.getPostID() == id){
                return index;
            }

            ++index;
        }
        return -1;
    }
}
