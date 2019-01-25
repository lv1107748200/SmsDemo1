package com.hxj.sms;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.com.UserInforConfig;
import com.xxbm.sbecomlibrary.net.base.BaseDataResponse;
import com.xxbm.sbecomlibrary.net.http.HttpCallback;
import com.xxbm.sbecomlibrary.net.http.HttpException;
import com.xxbm.sbecomlibrary.utils.CheckUtil;
import com.xxbm.sbecomlibrary.utils.NToast;
import com.xxbm.sbecomlibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * lv   2019/1/23
 */
public class LoginActivity  extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.pass_et)
    EditText pass_et;
    @BindView(R.id.login_btn)
    Button login_btn;

    @OnClick(R.id.login_btn)
    public void  click(View view){
        login_btn.setEnabled(false);
        login(phone_et.getText().toString(),pass_et.getText().toString());
    }
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }


    private void login(String username,String password){

        baseService.login(username, password, new HttpCallback<Object, BaseDataResponse<Object>>() {
            @Override
            public void onNotNet() {
                super.onNotNet();
                login_btn.setEnabled(true);
            }

            @Override
            public void onError(HttpException e) {
                NToast.shortToastBaseApp("登陆失败");
                login_btn.setEnabled(true);
            }

            @Override
            public void onSuccessAll(BaseDataResponse<Object> k) {
                login_btn.setEnabled(true);
                String userid = k.getUserid();
                if(!CheckUtil.isEmpty(userid)){
                    NToast.shortToastBaseApp("登陆成功");

                    SPUtils.putString(UserInforConfig.USERNAME,userid,false);

                    Intent intent = new Intent(LoginActivity.this,CardActivity.class);
                    intent.putExtra(CardActivity.CARDTYPE,userid);
                    startActivity(intent);
                    finish();
                }else {
                    NToast.shortToastBaseApp("登陆失败");
                }
            }
        },this.bindUntilEvent(ActivityEvent.DESTROY));

    }


}
