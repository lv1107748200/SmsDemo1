package com.xxbm.sbecomlibrary.net.base;





import android.os.Build;


import com.xxbm.sbecomlibrary.base.BaseApplation;
import com.xxbm.sbecomlibrary.net.Service.ExampleService;
import com.xxbm.sbecomlibrary.net.entry.response.CardListData;
import com.xxbm.sbecomlibrary.utils.CommonUtils;
import com.xxbm.sbecomlibrary.utils.NetStateUtils;
import com.xxbm.sbecomlibrary.widget.UserInfoManger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.xxbm.sbecomlibrary.net.Service.AppService;
import com.xxbm.sbecomlibrary.net.Service.UserService;
import com.xxbm.sbecomlibrary.net.Service.WXUserService;
import com.xxbm.sbecomlibrary.net.entry.request.Reg;
import com.xxbm.sbecomlibrary.net.entry.response.ALiYan;
import com.xxbm.sbecomlibrary.net.entry.response.Album;
import com.xxbm.sbecomlibrary.net.entry.response.Base;
import com.xxbm.sbecomlibrary.net.entry.response.Category;
import com.xxbm.sbecomlibrary.net.entry.response.ComAll;
import com.xxbm.sbecomlibrary.net.entry.response.Index;
import com.xxbm.sbecomlibrary.net.entry.response.UserInfo;
import com.xxbm.sbecomlibrary.net.entry.response.Vendor;
import com.xxbm.sbecomlibrary.net.entry.response.Ver;
import com.xxbm.sbecomlibrary.net.entry.response.WXAccessData;
import com.xxbm.sbecomlibrary.net.entry.response.YZMOrSID;
import com.xxbm.sbecomlibrary.net.http.HttpCallback;
import com.xxbm.sbecomlibrary.net.http.HttpUtils;
import com.xxbm.sbecomlibrary.net.subscriber.HttpSubscriber;
import com.xxbm.sbecomlibrary.net.subscriber.HttpUserSubscriber;
import com.xxbm.sbecomlibrary.net.subscriber.HttpWXUserSubscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.xxbm.sbecomlibrary.net.http.HttpModule.APP_CODE_VALUE;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseService {

    public BaseService() {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
    }
    @Inject
    public ExampleService exampleService;

    @SuppressWarnings("unchecked")
    public void login(String username,String password,
            HttpCallback<Object,BaseDataResponse<Object>> httpCallback
    ,ObservableTransformer transformer
    ) {
        if(!NetStateUtils.isNetworkConnected(BaseApplation.getBaseApp())){
            httpCallback.onNotNet();
            return;
        }
        Observable observable =  exampleService.login(username,password);
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<Object>>(httpCallback)
                ,transformer
        );
    }
    @SuppressWarnings("unchecked")
    public void getCardList(String userid,
                      HttpCallback<List<CardListData>,BaseDataResponse<List<CardListData>>> httpCallback
            ,ObservableTransformer transformer
    ) {
        if(!NetStateUtils.isNetworkConnected(BaseApplation.getBaseApp())){
            httpCallback.onNotNet();
            return;
        }
        Observable observable =  exampleService.getCardList(userid);
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<List<CardListData>>>(httpCallback)
                ,transformer
        );
    }

    @SuppressWarnings("unchecked")
    public void sendData(String sms_userid,String sms_cardid,String sms_date,String sms_body,
                      HttpCallback<Object,BaseDataResponse<Object>> httpCallback
            ,ObservableTransformer transformer
    ) {
        if(!NetStateUtils.isNetworkConnected(BaseApplation.getBaseApp())){
            httpCallback.onNotNet();
            return;
        }
        Observable observable =  exampleService.sendData(sms_userid,sms_cardid,sms_date,sms_body);
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<Object>>(httpCallback)
                ,transformer
        );
    }



}
