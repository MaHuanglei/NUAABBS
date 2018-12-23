package com.example.nuaabbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.nuaabbs.common.LogUtil;

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("MainActivity-BaseFragment", getClass().getSimpleName());
    }
}
