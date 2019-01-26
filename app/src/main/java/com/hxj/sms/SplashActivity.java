package com.hxj.sms;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.utils.CheckUtil;
import com.xxbm.sbecomlibrary.utils.NLog;
import com.xxbm.sbecomlibrary.utils.SPUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.xxbm.sbecomlibrary.com.UserInforConfig.USERNICKNAME;

/*
 * lv   2018/9/8
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            //获取ActivityManager
            ActivityManager mAm = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            //获得当前运行的task
            List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo rti : taskList) {
                //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
                if(rti.topActivity.getPackageName().equals(getPackageName())) {
                    mAm.moveTaskToFront(rti.id,ActivityManager.MOVE_TASK_WITH_HOME);
                    finish();
                    break;
                }
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initTitle() {
        super.initTitle();

    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        super.initView();

        Observable.timer(3, TimeUnit.SECONDS).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        String id = SPUtils.getString(USERNICKNAME,"");
                        NLog.e(NLog.TAGOther,"---> " + id);
                        if(CheckUtil.isEmpty(id)){
                            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                        }

                        finish();
                    }
                });

    }

    @Override
    public void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
