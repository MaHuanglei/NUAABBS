package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nuaabbs.R;

public class SystemSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, SystemSettingActivity.class);
        context.startActivity(intent);
    }
}
