package com.example.nuaabbs.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.nuaabbs.MainActivity;
import com.example.nuaabbs.R;
import com.example.nuaabbs.common.CommonCache;


public class PopUpEditWindow {

    Context mContext;
    View callBackView;
    View dropView;
    String param;

    PopupWindow popupWindow;
    EditText inputComment;

    String inputContent = "$#!cancel!#$";

    public PopUpEditWindow(Context context, View callBackView, String param, View dropView){
        this.mContext = context;
        this.callBackView = callBackView;
        this.param = param;
        this.dropView = dropView;
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

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

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
        if(CommonCache.CurrentActivity.getActivityNum() == 0)
            ((MainActivity)mContext).HideFAB();


        // 设置弹出窗体需要软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //popupWindow.showAsDropDown(dropView);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(CommonCache.CurrentActivity.getActivityNum() == 1)
                    ((MainActivity)mContext).ShowFAB();
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
