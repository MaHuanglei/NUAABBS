package com.example.nuaabbs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.action.CreatePostActivity;
import com.example.nuaabbs.action.LoginActivity;
import com.example.nuaabbs.action.MyCareActivity;
import com.example.nuaabbs.action.MyCollectActivity;
import com.example.nuaabbs.action.MyDraftActivity;
import com.example.nuaabbs.action.MyPostActivity;
import com.example.nuaabbs.action.PersonalPageActivity;
import com.example.nuaabbs.action.SearchActivity;
import com.example.nuaabbs.action.SystemSettingActivity;
import com.example.nuaabbs.adapter.MyPageFragmentAdapter;
import com.example.nuaabbs.asyncNetTask.LogoutTask;
import com.example.nuaabbs.common.MyApplication;
import com.example.nuaabbs.fragment.BaseFragment;
import com.example.nuaabbs.fragment.HotPostFragment;
import com.example.nuaabbs.fragment.LifePostFragment;
import com.example.nuaabbs.fragment.StudyPostFragment;
import com.example.nuaabbs.object.ZoomOutPageTransformer;
import com.example.nuaabbs.util.LogUtil;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    ImageView headPortraitImageView;
    TextView loginStateTextView;
    TextView idiograph;

    NavigationView navigationView;
    Menu menu;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTitle = {"热点","生活","生活"};
    private List<BaseFragment> mFragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);
        headPortraitImageView = headerView.findViewById(R.id.headPortrait);
        headPortraitImageView.setOnClickListener(this);
        loginStateTextView = headerView.findViewById(R.id.loginState);
        loginStateTextView.setOnClickListener(this);
        idiograph = headerView.findViewById(R.id.idiograph);
        idiograph.setOnClickListener(this);

        initData();
        mTabLayout = findViewById(R.id.post_tab);
        if(mTabLayout == null){
            LogUtil.e("MainActivity", "tabLayout = null");
        }


        mViewPager = findViewById(R.id.post_pager);
        MyPageFragmentAdapter mAdapter =
                new MyPageFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitle);

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }

    private void initData()
    {
        HotPostFragment hotPostFragment = new HotPostFragment();
        LifePostFragment lifePostFragment = new LifePostFragment();
        StudyPostFragment studyPostFragment = new StudyPostFragment();
        mFragmentList.add(hotPostFragment);
        mFragmentList.add(lifePostFragment);
        mFragmentList.add(studyPostFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshNavigationView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            SearchActivity.actionStart(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()){
            case R.id.nav_post:
                MyPostActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_drafts:
                MyDraftActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_care:
                MyCareActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_collection:
                MyCollectActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_setting:
                SystemSettingActivity.actionStart(MainActivity.this);
                break;
            case R.id.nav_logout:
                if(!MyApplication.loginState){
                    Toast.makeText(this,"你还没有登录！", Toast.LENGTH_SHORT).show();
                    break;
                }
                LogoutTask logoutTask = new LogoutTask(this);
                logoutTask.execute();
                break;
            default:
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                CreatePostActivity.actionStart(MainActivity.this);
                break;
            case R.id.headPortrait:
            case R.id.loginState:
            case R.id.idiograph:
                if(MyApplication.loginState){
                    PersonalPageActivity.actionStart(MainActivity.this);
                }else{
                    LoginActivity.actionStart(MainActivity.this);
                }
                break;
            default:
        }
    }

    public void RefreshNavigationView(){
        if(MyApplication.loginState){
            loginStateTextView.setText(MyApplication.userInfo.getUserName());
            idiograph.setText(MyApplication.userInfo.getIdioGraph());
        }else{
            loginStateTextView.setText(R.string.nav_header_title);
            idiograph.setText(R.string.nav_header_subtitle);
        }
    }


}
