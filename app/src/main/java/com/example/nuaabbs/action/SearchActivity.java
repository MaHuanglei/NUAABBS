package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nuaabbs.R;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
