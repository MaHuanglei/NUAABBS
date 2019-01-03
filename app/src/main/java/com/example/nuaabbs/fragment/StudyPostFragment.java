package com.example.nuaabbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.asyncNetTask.RequestLabelPostTask;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.object.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudyPostFragment extends BaseFragment {
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;
    private SwipeRefreshLayout swipeRefresh;

    public StudyPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_post, container, false);

        swipeRefresh = view.findViewById(R.id.study_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPost();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.study_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(getContext(),postList, false);
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    private void initPosts(){
        refreshPost();
    }

    private void refreshPost(){
        RequestLabelPostTask requestLabelPostTask =
                new RequestLabelPostTask(getActivity(), this);
        requestLabelPostTask.execute(Constant.REQCODE_STUDY_Post);
    }

    public void RequestSuccess(List<Post> newPostList){
        postList.addAll(0, newPostList);
        postAdapter.notifyDataSetChanged();
        closeRefreshBar(true);
    }

    public void RequestFail(){
        closeRefreshBar(false);
    }

    public void closeRefreshBar(boolean showToast){
        if(swipeRefresh != null && swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
            if(showToast)
                Toast.makeText(getActivity(), "更新成功!", Toast.LENGTH_SHORT).show();
        }
    }
}
