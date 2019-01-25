package com.xxbm.sbecomlibrary.net.Service;



import com.xxbm.sbecomlibrary.net.base.BaseDataResponse;
import com.xxbm.sbecomlibrary.net.entry.response.CardListData;
import com.xxbm.sbecomlibrary.net.entry.response.Ver;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by 吕 on 2017/10/27.
 */

public interface ExampleService {

    @GET("app.html")
    Observable<Response<BaseDataResponse<Object>>> login(@Query("username") String username, @Query("password") String password);

    @GET("app.html")
    Observable<Response<BaseDataResponse<List<CardListData>>>> getCardList(@Query("userid") String userid);


    @GET("app.html")
    Observable<Response<BaseDataResponse<Object>>> sendData(@Query("sms_userid") String sms_userid,@Query("sms_cardid") String sms_cardid
    ,@Query("sms_date") String sms_date,@Query("sms_body") String  sms_body);


}
