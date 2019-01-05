package com.example.nuaabbs.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.nuaabbs.R;


public class PopUpEditWindow {
    Context mContext;
    View callBackView;
    String param;

    PopupWindow popupWindow;
    EditText inputComment;

    String inputContent = "$#!cancel!#$";

    public PopUpEditWindow(Context context, View callBackView, String param){
        this.mContext = context;
        this.callBackView = callBackView;
        this.param = param;
    }

    public void show(){
        if(popupWindow == null) PrepareWindow();
        popupWindow.update();
    }

    public String getInputContent(){
        return inputContent;
    }

    private void PrepareWindow(){
        final View view = LayoutInflater.from(mContext).inflate(R.layout.comment_popupwindow, null);
        inputComment = view.findViewById(R.id.et_discuss);
        inputComment.setHint(param);

        TextView btn_submit = view.findViewById(R.id.tv_confirm);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                return false;

            }
        });

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);


        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popuwindow_white_bg));


        // 设置弹出窗体需要软键盘
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        //popupInputMethodWindow();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String commentInfo = inputComment.getText().toString();
                if (!commentInfo.isEmpty()) {
                    inputContent = commentInfo;
                    popupWindow.dismiss();
                    callBackView.performClick();
                }

            }
        });
    }
}
