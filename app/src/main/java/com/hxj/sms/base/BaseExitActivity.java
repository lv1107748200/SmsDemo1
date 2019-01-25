package com.hxj.sms.base;

import android.content.Intent;
import android.view.KeyEvent;

import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.utils.NToast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * Created by 吕 on 2017/11/9.
 */

public class BaseExitActivity extends BaseActivity {
    private boolean exit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!exit) {
                NToast.shortToastBaseApp("返回桌面");
                exit = true;

                Observable.timer(2, TimeUnit.SECONDS).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                exit = false;
                            }
                        });

                return true;
            }else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return super.onKeyDown(KeyEvent.KEYCODE_BACK, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
