package com.example.nuaabbs.action;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.nuaabbs.R;
import com.example.nuaabbs.adapter.PostAdapter;
import com.example.nuaabbs.common.CommonCache;
import com.example.nuaabbs.common.Constant;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.util.ChoosePhotoUtil;
import com.example.nuaabbs.util.TakePhotoUtil;
import com.sun.easysnackbar.BaseTransientBar;
import com.sun.easysnackbar.EasySnackBar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalPageActivity extends BaseActivity implements View.OnClickListener{

    View contentView;
    EasySnackBar snackBar;
    CircleImageView headImg;
    PostAdapter adapter;
    RecyclerView personalPageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        Toolbar toolbar = findViewById(R.id.personal_toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        headImg = findViewById(R.id.personal_headImg);
        headImg.setOnClickListener(this);
        personalPageRecyclerView = findViewById(R.id.personal_page_RecyclerView);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton addPost = findViewById(R.id.personal_addPost);
        addPost.setOnClickListener(this);

        Glide.with(this).load(R.drawable.plane).into(headImg);
        collapsingToolbar.setTitle(MyApplication.userInfo.getUserName());

        if(MyApplication.postListManager.getMyPostList().isEmpty()){
            MyApplication.postListManager.refreshMyPostList(Constant.PersonalPageActivityRequestTask);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        personalPageRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(this,
                MyApplication.postListManager.getMyPostList(), true);
        personalPageRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        CommonCache.CurrentActivity.setCurrentActivity(Constant.PersonalPageActivityNum, this);
        if(MyApplication.postListManager.isMyPostListChanged()){
            this.adapter.notifyDataSetChanged();
            MyApplication.postListManager.setMyPostListChanged(false);
        }
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, PersonalPageActivity.class);
        context.startActivity(intent);
    }

    public void DealRequestResult(boolean successFlag){
        if(successFlag) adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.person, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_show_detail:
                UserDetailActivity.actionStart(PersonalPageActivity.this);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_addPost:
                CreatePostActivity.actionStart(PersonalPageActivity.this);
                break;
            case R.id.personal_headImg:
                contentView = EasySnackBar.convertToContentView(v, R.layout.edit_head_img);
                contentView.findViewById(R.id.personal_watch_headImg).setOnClickListener(this);
                contentView.findViewById(R.id.personal_choose_from_album).setOnClickListener(this);
                contentView.findViewById(R.id.personal_take_photo).setOnClickListener(this);
                snackBar = EasySnackBar.make(v, contentView, BaseTransientBar.LENGTH_LONG, false);
                snackBar.show();
                break;
            case R.id.personal_watch_headImg:
                Toast.makeText(this,"查看头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.personal_choose_from_album:
                ChoosePhotoUtil.choosePhoto(this);
                snackBar.dismiss();
                break;
            case R.id.personal_take_photo:
                TakePhotoUtil.executeTakePhoto(this);
                snackBar.dismiss();
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ChoosePhotoUtil.openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case Constant.TAKE_PHOTO :
                if(resultCode == RESULT_OK){
                    try{
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(TakePhotoUtil.imageUri));
                        headImg.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constant.CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath;
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        imagePath = ChoosePhotoUtil.handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        imagePath = ChoosePhotoUtil.handleImageBeforeKitKat(data);
                    }
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            headImg.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
