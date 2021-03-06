package com.xxbm.sbecomlibrary.base.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.xxbm.sbecomlibrary.R;
import com.xxbm.sbecomlibrary.base.BaseApplation;
import com.xxbm.sbecomlibrary.net.base.BaseService;
import com.xxbm.sbecomlibrary.utils.DisplayUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;



/**
 * Created by 吕 on 2017/10/26.
 */

public class BaseActivity extends AbstractBaseActivity{
    @Inject
    public BaseService baseService;
    Unbinder unbinder;


    public View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //setStatusBar();
        super.onCreate(savedInstanceState);
        BaseApplation.getBaseApp().getAppComponent().inject(this);
        setContentView(getLayout());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
         view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder =  ButterKnife.bind(this);

        initTitle();
        initView();
        initView(getIntent());
        initData();
        initData(getIntent());
    }
    public boolean isStatusBar(){
        return true;
    }

    /**
     * @param outState 取消保存状态
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState 取消保存状态
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public int  getLayout() {
        return 0;
    }



    @Override
    public void initTitle() {

    }
    @Override
    public void initView() {

    }
    @Override
    public void initData() {

    }
    public void initView(Intent intent) {

    }
    public void initData(Intent intent) {

    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        if(null != unbinder){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (true) {
             //   getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt int getStatusBarColor() {
        return Color.WHITE;
    }

}
