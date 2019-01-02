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
    private Post[] posts = {new Post(),new Post(),new Post(),new Post(),
            new Post(),new Post(),new Post(),new Post()};

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
        postAdapter = new PostAdapter(getContext(),postList);
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    private void initPosts(){
        postList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(posts.length);
            postList.add(posts[index]);
        }
    }
}
