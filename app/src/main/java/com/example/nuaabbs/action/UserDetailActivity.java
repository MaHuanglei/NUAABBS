package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nuaabbs.R;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.util.LogUtil;

public class UserDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            LogUtil.d("UserDetailActivity", "actionBar != null");
        }else{
            LogUtil.d("UserDetailActivity", "actionBar = null");
        }

        TextView userName = findViewById(R.id.detail_user_name);
        userName.setText(MyApplication.userInfo.getUserName());

        TextView userSchoolID = findViewById(R.id.detail_user_schoolID);
        userSchoolID.setText(MyApplication.userInfo.getSchoolID());

        TextView userSex = findViewById(R.id.detail_user_sex);
        userSex.setText(MyApplication.userInfo.getSex());

        TextView userBirthday = findViewById(R.id.detail_user_birthday);
        if(MyApplication.userInfo.isBirthdayEffect())
            userBirthday.setText(MyApplication.userInfo.getBirthday().toString());
        else userBirthday.setText("");

        TextView userIdiograph = findViewById(R.id.detail_user_idiograph);
        userIdiograph.setText(MyApplication.userInfo.getIdioGraph());
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, UserDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return true;
    }
}
