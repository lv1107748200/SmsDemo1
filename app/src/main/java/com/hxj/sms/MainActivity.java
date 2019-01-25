package com.hxj.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hxj.sms.R;
import com.hxj.sms.base.BaseExitActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.com.UserInforConfig;
import com.xxbm.sbecomlibrary.net.base.BaseDataResponse;
import com.xxbm.sbecomlibrary.net.entry.response.CardListData;
import com.xxbm.sbecomlibrary.net.http.HttpCallback;
import com.xxbm.sbecomlibrary.net.http.HttpException;
import com.xxbm.sbecomlibrary.utils.CheckUtil;
import com.xxbm.sbecomlibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseExitActivity {
	public static final String  CARDTYPE = "MainActivity";
	SmsObserver mSmsObserver;
	TextView mSmsTv;
	private CardListData cardListData;

	private String hhhhh = "";

	@BindView(R.id.tv_card)
			TextView tv_card;

	@OnClick(R.id.btn_close)
			public void click(View view){
		finish();
	}

	Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			Bundle bd = msg.getData();
			if(bd.getString("msg") != null){

				String what = bd.getString("msg");
				mSmsTv.setText(what);

			}
		};
	};

	@Override
	public int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void initView() {
		super.initView();
		mSmsObserver = new SmsObserver(mHandler);

		getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, mSmsObserver);

		mSmsTv = (TextView) findViewById(R.id.tv);
	}

	@Override
	public void initData(Intent intent) {
		super.initData(intent);

		cardListData = intent.getParcelableExtra(CARDTYPE);

		if(!CheckUtil.isEmpty(cardListData)){
			String numCard = cardListData.getCardNo();

			tv_card.setText(cardListData.getBankName()+"    "+ numCard+"   " + cardListData.getCardName());
			hhhhh = numCard.substring(numCard.length() - 4, numCard.length());
			getSms();
		}
	}


	class SmsObserver extends ContentObserver{

		public SmsObserver(Handler handler) {
			
			super(handler);
			
		}
		
		@Override
		public void onChange(boolean selfChange) {
			
			super.onChange(selfChange);


                getSms();

		}

	}
	
	private void getSms() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				ContentResolver cr = getContentResolver();
				String [] projection = new String[] {"_id","thread_id","address","person","date","protocol","read","status","type","body","service_center"};
				
				String where = " date > " + (System.currentTimeMillis() - 1000);
				
				Cursor cur = cr.query(Uri.parse("content://sms/"), projection, where, null, "date desc");
				
				
				List<SmsEntity> mList = new ArrayList<SmsEntity>();
				
				if(cur != null && cur.moveToFirst()){
					
					do{
						SmsEntity mEntity = new SmsEntity();
						mEntity.body = cur.getString(cur.getColumnIndex("body"));

						if(mEntity.body.indexOf(hhhhh)!=-1){
							mEntity.id = cur.getInt(cur.getColumnIndex("_id"));
							mEntity.thread_id = cur.getInt(cur.getColumnIndex("thread_id"));
							mEntity.address = cur.getString(cur.getColumnIndex("address"));
							mEntity.person = cur.getString(cur.getColumnIndex("person"));
							mEntity.date = cur.getString(cur.getColumnIndex("date"));
							mEntity.protocol = cur.getString(cur.getColumnIndex("protocol"));
							mEntity.read = cur.getInt(cur.getColumnIndex("read"));
							mEntity.status = cur.getInt(cur.getColumnIndex("status"));
							mEntity.type = cur.getInt(cur.getColumnIndex("type"));

							mEntity.service_center = cur.getString(cur.getColumnIndex("service_center"));
							mList.add(mEntity);

							sendData(mEntity.date,mEntity.body);
						}



					}while(cur.moveToNext());
				}
				
				if(cur != null){
					cur.close();
				}
				Message msg = mHandler.obtainMessage();
				Bundle bd = new Bundle();
				bd.putString("msg", SmsEntity.getListString(mList));
				msg.setData(bd);
				
				mHandler.sendMessage(msg);
				
			}
		}).start();
		
	}


	private void sendData(String sms_date,String sms_body){
		baseService.sendData(SPUtils.getString(UserInforConfig.USERNAME,"1"), cardListData.getCardId(), sms_date, sms_body, new HttpCallback<Object, BaseDataResponse<Object>>() {
			@Override
			public void onError(HttpException e) {

			}

			@Override
			public void onNotNet() {
				super.onNotNet();
			}

			@Override
			public void onSuccessAll(BaseDataResponse<Object> k) {
				super.onSuccessAll(k);
			}
		},this.bindUntilEvent(ActivityEvent.DESTROY));
	}
}
