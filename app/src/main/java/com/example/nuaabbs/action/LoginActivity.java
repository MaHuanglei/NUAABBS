package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.LoginTask;
import com.example.nuaabbs.common.MyApplication;

public class LoginActivity extends BaseActivity
                        implements View.OnClickListener{

    EditText userName, password;
    CheckBox remPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        remPassword = findViewById(R.id.remember_password);
        loadLastLoginInfo();

        Button signIn = findViewById(R.id.login_btn);
        Button register = findViewById(R.id.register_btn);
        signIn.setOnClickListener(this);
        register.setOnClickListener(this);
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
            case R.id.login_btn:
                saveThisLoginInfo();
                if(!checkLoginInfo()) break;
                LoginTask loginTask = new LoginTask(this,findViewById(R.id.login_process_bar));
                loginTask.execute(userName.getText().toString(), password.getText().toString());
                break;
            case R.id.register_btn:
                RegisterActivity.actionStart(LoginActivity.this);
                break;
            default:
        }
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.loginState)
            this.finish();
    }

    public void loginSuccess(){
        MyApplication.loginState = true;
        MyApplication.userInfo.PrintUserInfo();
        this.finish();
    }

    private void loadLastLoginInfo(){
        SharedPreferences spf = getSharedPreferences("loginData", 0);

        String tmpStr = spf.getString("userName", "xxxxx");
        if(!tmpStr.equals("xxxxx"))
            userName.setText(tmpStr);

        boolean tmpBool = spf.getBoolean("remPassword", false);
        remPassword.setChecked(tmpBool);

        tmpStr = spf.getString("password", "#####");
        if(!tmpStr.equals("#####") && tmpBool)
            password.setText(tmpStr);
    }

    private void saveThisLoginInfo(){
        SharedPreferences spf = getSharedPreferences("loginData", 0);
        SharedPreferences.Editor  editor = spf.edit();

        String tmpStr = userName.getText().toString();
        editor.putString("userName", tmpStr);

        tmpStr = password.getText().toString();
        editor.putString("password", tmpStr);

        boolean tmpBool = remPassword.isChecked();
        editor.putBoolean("remPassword", tmpBool);

        editor.apply();
    }

    private boolean checkLoginInfo(){

        String tmpStr = userName.getText().toString();
        if(tmpStr.isEmpty()){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        tmpStr = password.getText().toString();
        if(tmpStr.isEmpty()){
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
