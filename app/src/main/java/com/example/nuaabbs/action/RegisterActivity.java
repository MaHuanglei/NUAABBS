package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.RegisterTask;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    EditText reg_userName, reg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Button reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(this);
        reg_userName = findViewById(R.id.reg_user_name);
        reg_password = findViewById(R.id.reg_password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.RegisterActivityNum, this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn:
                if(!checkRegisterInfo())break;
                beginRegister();
                break;
            default:
        }
    }

    private boolean checkRegisterInfo(){
        String tmpStr = reg_userName.getText().toString();
        if(tmpStr.isEmpty()){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        tmpStr = reg_password.getText().toString();
        if(tmpStr.isEmpty()){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void beginRegister(){
        String tmp, param;
        tmp = reg_userName.getText().toString();
        MyApplication.userInfo.setUserName(tmp);
        param = tmp;

        tmp = reg_password.getText().toString();
        MyApplication.userInfo.setPassword(tmp);
        param += ":&:" + tmp;

        EditText reg_schoolID = findViewById(R.id.reg_schoolID);
        tmp = reg_schoolID.getText().toString().trim();
        if(TextUtils.isEmpty(tmp)) tmp = "null";
        else MyApplication.userInfo.setSchoolID(tmp);
        param += ":&:" + tmp;

        RadioGroup reg_sexGroup = findViewById(R.id.reg_sexGroup);
        int id = reg_sexGroup.getCheckedRadioButtonId();
        if(id == R.id.reg_sex_man) {
            tmp = "男";
            MyApplication.userInfo.setSex("男");
        } else if(id == R.id.reg_sex_woman){
            tmp = "女";
            MyApplication.userInfo.setSex("女");
        } else{
            tmp = "null";
            MyApplication.userInfo.setSex("保密");
        }

        param += ":&:" + tmp;

        RegisterTask regTask =
                new RegisterTask(this, (ProgressBar)findViewById(R.id.reg_proBar));
        regTask.execute(param);
    }

    public void registerSuccess(int id){
        MyApplication.loginState = true;
        MyApplication.userInfo.setId(id);
        saveLoginInfo();
        MyApplication.userInfo.PrintUserInfo();
        this.finish();
    }

    public void saveLoginInfo(){
        SharedPreferences spf = getSharedPreferences("loginData", 0);
        SharedPreferences.Editor  editor = spf.edit();

        editor.putString("userName", MyApplication.userInfo.getUserName());
        editor.putString("password", MyApplication.userInfo.getPassword());
        editor.putBoolean("remPassword", true);

        editor.apply();
    }
}
