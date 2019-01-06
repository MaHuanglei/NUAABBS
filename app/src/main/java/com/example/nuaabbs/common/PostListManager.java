package com.example.nuaabbs.common;

import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;

public class PostListManager {

    public static boolean myPostListChanged = false;
    public static boolean hotPostListChanged = false;
    public static boolean studyPostListChanged = false;
    public static boolean lifePostListChanged = false;

    public static List<Post> myPostList = new ArrayList<>();
    public static List<Post> hotPostList = new ArrayList<>();
    public static List<Post> studyPostList = new ArrayList<>();
    public static List<Post> lifePostList = new ArrayList<>();

    public PostListManager(){

    }

    public static void refreshMyPostList(List<Post> newPostList){
        refreshPostListFromTime(newPostList, myPostList);
    }

    public static void refreshLifePostList(List<Post> newPostList){
        refreshPostListFromTime(newPostList, lifePostList);
    }

    public static void refreshStudyPostList(List<Post> newPostList){
        refreshPostListFromTime(newPostList, studyPostList);
    }

    public static void refreshHotPostList(List<Post> newPostList){
        int index;
        for(Post newPost:newPostList){
            index = findPost(newPost, hotPostList);
            if(index != -1) hotPostList.remove(index);

            InsertPostFromHotDegree(newPost, hotPostList);
        }
    }

    public static void addNewMyPost(Post newPost){
        myPostList.add(0, newPost);
        myPostListChanged = true;
    }

    public static void addNewLifePost(Post newPost){
        lifePostList.add(0, newPost);
        lifePostListChanged = true;
    }

    public static void addNewStudyPost(Post newPost){
        studyPostList.add(0, newPost);
        studyPostListChanged = true;
    }

    public static void addNewHotPost(Post newPost){
        InsertPostFromHotDegree(newPost, hotPostList);
        hotPostListChanged = true;
    }

    private static void refreshPostListFromTime(List<Post> newPostList, List<Post> oldPostList){
        int index;
        for(Post newPost:newPostList){
            index = findPost(newPost, oldPostList);
            if(index != -1) oldPostList.remove(index);

            InsertPostFromTime(newPost, oldPostList);
        }
    }

    private static int findPost(Post newPost, List<Post> postList){
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

    private static void InsertPostFromTime(Post newPost, List<Post> postList){
        int newPostID = newPost.getPostID();
        int index = 0;
        for(Post post: postList){
            if(newPostID > post.getPostID()){
                postList.add(index, newPost);
                return;
            }
            ++index;
        }
        postList.add(newPost);
    }

    private static void InsertPostFromHotDegree(Post newPost, List<Post> postList){
        int newPostHotDegree = newPost.getHotDegree();
        int index = 0;
        for(Post post: postList){
            if(newPostHotDegree > post.getHotDegree()){
                postList.add(index, newPost);
                return;
            }
            ++index;
        }
        postList.add(newPost);
    }
}
