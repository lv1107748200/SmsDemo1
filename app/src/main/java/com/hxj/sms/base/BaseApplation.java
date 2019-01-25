package com.hxj.sms.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;




/**
 * Created by 吕 on 2017/10/26.
 */

public class BaseApplation extends com.xxbm.sbecomlibrary.base.BaseApplation {


    private static Activity sActivity = null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

        }
    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static Activity getActivity(){
        return sActivity;
    }

}
