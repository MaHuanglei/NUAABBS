package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nuaabbs.R;

public class MyPostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, MyPostActivity.class);
        context.startActivity(intent);
    }
}
