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
import com.example.nuaabbs.asyncNetTask.RequestHotPostTask;
import com.example.nuaabbs.common.PostListManager;
import com.example.nuaabbs.object.Post;

import java.util.List;

public class HotPostFragment extends BaseFragment {

    private List<Post> postList = PostListManager.hotPostList;
    private PostAdapter postAdapter;
    private SwipeRefreshLayout swipeRefresh;

    public HotPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPosts();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(PostListManager.hotPostListChanged){
            this.postAdapter.notifyDataSetChanged();
            PostListManager.hotPostListChanged = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_post, container, false);

        swipeRefresh = view.findViewById(R.id.hot_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPost();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.hot_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(getContext(), postList, true);
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    private void initPosts(){
        if(PostListManager.hotPostList.isEmpty())
            refreshPost();
    }

    private void refreshPost(){
        RequestHotPostTask requestHotPostTask = new RequestHotPostTask(getActivity(), this);
        requestHotPostTask.execute("");
    }

    public void RequestSuccess(){

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
