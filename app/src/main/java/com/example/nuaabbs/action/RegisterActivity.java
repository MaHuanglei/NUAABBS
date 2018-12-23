package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nuaabbs.R;
import com.example.nuaabbs.asyncNetTask.RegisterTask;
import com.example.nuaabbs.common.MyApplication;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    EditText reg_userName, reg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(this);
        reg_userName = findViewById(R.id.reg_user_name);
        reg_password = findViewById(R.id.reg_password);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
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
        param = tmp;

        tmp = reg_password.getText().toString();
        param += ":&:" + tmp;

        EditText reg_schoolID = findViewById(R.id.reg_schoolID);
        tmp = reg_schoolID.getText().toString();
        if(TextUtils.isEmpty(tmp)) tmp = "null";
        param += ":&:" + tmp;

        RadioGroup reg_sexGroup = findViewById(R.id.reg_sexGroup);
        int id = reg_sexGroup.getCheckedRadioButtonId();
        if(id == R.id.reg_sex_man) tmp = "男";
        else if(id == R.id.reg_sex_woman) tmp = "女";
        else  tmp = "null";

        param += ":&:" + tmp;

        RegisterTask regTask =
                new RegisterTask(this, (ProgressBar)findViewById(R.id.reg_proBar));
        regTask.execute(param);
    }

    public void registerSuccess(){
        MyApplication.loginState = true;
        MyApplication.userInfo.PrintUserInfo();
        this.finish();
    }
}
