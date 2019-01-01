package com.example.nuaabbs.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LifePostFragment extends BaseFragment {
    private Post[] posts = {new Post("刘洋",""),new Post("马黄雷", ""),
            new Post("左安发",""),new Post("董伟良", ""),
            new Post("卢泽普",""),new Post("石一泽", ""),
            new Post("刘爱媛",""),new Post("潘贵", "")};

    private List<Post> postList = new ArrayList<>();

    private PostAdapter postAdapter;

    public LifePostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life_post, container, false);

        initPosts();
        RecyclerView recyclerView = view.findViewById(R.id.life_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    private void initPosts(){
        for(int i=0;i<posts.length;++i)
            posts[i].setPostInfo("学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！"
                    + "学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！"
                    + "学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！"
                    + "学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！"
                    + "学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！学习使我快乐！");

        postList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(posts.length);
            postList.add(posts[index]);
        }
    }
}
