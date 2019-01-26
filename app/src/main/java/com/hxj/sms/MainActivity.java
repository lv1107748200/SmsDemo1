package com.hxj.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.Manifest;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hxj.sms.R;
import com.hxj.sms.adapter.QuickAdapter;
import com.hxj.sms.base.BaseExitActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.base.activity.PermissionCallback;
import com.xxbm.sbecomlibrary.com.UserInforConfig;
import com.xxbm.sbecomlibrary.net.base.BaseDataResponse;
import com.xxbm.sbecomlibrary.net.entry.response.CardListData;
import com.xxbm.sbecomlibrary.net.http.HttpCallback;
import com.xxbm.sbecomlibrary.net.http.HttpException;
import com.xxbm.sbecomlibrary.utils.CheckUtil;
import com.xxbm.sbecomlibrary.utils.DateUtils;
import com.xxbm.sbecomlibrary.utils.NLog;
import com.xxbm.sbecomlibrary.utils.NToast;
import com.xxbm.sbecomlibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.hxj.sms.SJCardID.getCaraNum;
import static com.hxj.sms.SendMessage.getWorkHandler;
import static com.xxbm.sbecomlibrary.com.UserInforConfig.USERNAME;
import static com.xxbm.sbecomlibrary.com.UserInforConfig.USERNICKNAME;
import static com.xxbm.sbecomlibrary.utils.DateUtils.DATE_FORMAT;
import static com.xxbm.sbecomlibrary.utils.DateUtils.getTIme;

public class MainActivity extends BaseExitActivity {
	public static final String  CARDTYPE = "MainActivity";
	SmsObserver mSmsObserver;
	private int tabNum;

	private StubYHK stubYHK;
	private StubRZXX stubRZXX;
	private StubYH stubYH;

	private List<SmsEntity> smsEntities;

	private long kqtime = 0;//开启时间

	@BindView(R.id.tv_what)
	TextView tv_what;

	@BindView(R.id.stub_yhk)
	ViewStub stub_yhk;
	@BindView(R.id.stub_rzxx)
	ViewStub stub_rzxx;
	@BindView(R.id.stub_yh)
	ViewStub stub_yh;

	@BindView(R.id.bottomBar)
	BottomBar bottomBar;
	@OnClick(R.id.tv_what)
	public void click(View view){
		if(tabNum == R.id.tab_favorites){
			SPUtils.putString(UserInforConfig.USERNICKNAME,"",true);
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	Handler mHandler = new Handler(){

		public void handleMessage(android.os.Message msg) {
//				List<SmsEntity> smsEntitiessss = (List<SmsEntity>) msg.obj;
//
//				if(!CheckUtil.isEmpty(smsEntitiessss)){
//					smsEntities.addAll(smsEntitiessss);
//				}
//
//				if(null != stubRZXX){
//					stubRZXX.upData(smsEntities);
//				}
		};
	};

	@Override
	public int getLayout() {
		return R.layout.activity_card;
	}

	@Override
	public void initView() {
		super.initView();
		smsEntities = new ArrayList<>();
		mSmsObserver = new SmsObserver(mHandler);
		getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, mSmsObserver);

		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(int tabId) {
				tabNum = tabId;
				NLog.e(NLog.TAGOther,"tabid--->" + tabId);
				if(tabId == R.id.tab_nearby){
					tv_what .setText("银行卡列表");
				}else if(tabId == R.id.tab_friends){
					tv_what.setText("监听日志");
				}else {
					tv_what.setText("退出");
				}
				setTab(tabId);
			}
		});
	}

	@Override
	public void initData() {
		super.initData();
		kqtime = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {
		NLog.e(NLog.TAGDOWN,"注销--->" );
		getContentResolver().unregisterContentObserver(mSmsObserver);
		super.onDestroy();
	}

	@Override
	public void initData(Intent intent) {
		super.initData(intent);
		getqx();//初始获取
	}

	private void getqx(){
		requestPermission(Manifest.permission.RECEIVE_SMS, new PermissionCallback() {
			@Override
			public void requestP(boolean own) {
				if(own){
					getSms();
				}
			}
		});
	}

	class SmsObserver extends ContentObserver{

		public SmsObserver(Handler handler) {
			
			super(handler);
			
		}
		
		@Override
		public void onChange(boolean selfChange) {
			
			super.onChange(selfChange);
			//NLog.e(NLog.TAGDOWN,"selfChange--->" + selfChange );

			getqx();//实时监听

		}

	}

	private boolean isTest = false;
	private final int time = 24*60*60*1000;


	
	private void getSms() {
		getWorkHandler().post(new Runnable() {
			@Override
			public void run() {

			}
		});

		new Thread(){
			@Override
			public void run() {
				cusssss();
			}
		}.start();
	}
	private void cusssss(){
		ContentResolver cr = getContentResolver();
		String [] projection = new String[] {"_id","thread_id","address","person","date","protocol","read","status","type","body","service_center"};

		long sf = System.currentTimeMillis();

		long jsfkdsl = (sf - (sf - kqtime));

		NLog.e(NLog.TAGDOWN,"开启时间 --->" + jsfkdsl);

		String where = " date > " + (jsfkdsl);

		Cursor cur = cr.query(Uri.parse("content://sms/"), projection, where, null, "date desc");


		List<SmsEntity> mList = new ArrayList<SmsEntity>();

		NLog.e(NLog.TAGDOWN,"有短信--->");

		if(cur != null && cur.moveToFirst()){

			do{
				SmsEntity mEntity = new SmsEntity();

				mEntity.body = cur.getString(cur.getColumnIndex("body"));
				//+getCaraNum();

				mEntity.date = cur.getString(cur.getColumnIndex("date"));

				mEntity.address = cur.getString(cur.getColumnIndex("address"));

				mEntity.id = cur.getInt(cur.getColumnIndex("_id"));

				Long hhhshijian = Long.parseLong(mEntity.date);




					SendMessage.CardAndWhat  cardAndWhat = SendMessage.baohan(mEntity.body);

					if(cardAndWhat.isIs()){

						if(SendMessage.getShangchuan(mEntity.address,new SendMessage.SMSHHHH(hhhshijian,mEntity.id))){

						mEntity.id = cur.getInt(cur.getColumnIndex("_id"));
						mEntity.thread_id = cur.getInt(cur.getColumnIndex("thread_id"));
						mEntity.person = cur.getString(cur.getColumnIndex("person"));
						mEntity.protocol = cur.getString(cur.getColumnIndex("protocol"));
						mEntity.read = cur.getInt(cur.getColumnIndex("read"));
						mEntity.status = cur.getInt(cur.getColumnIndex("status"));
						mEntity.type = cur.getInt(cur.getColumnIndex("type"));
						mEntity.cardId = cardAndWhat.getId();
						mEntity.service_center = cur.getString(cur.getColumnIndex("service_center"));
						//mEntity.cardId = "jjjj";
						mList.add(mEntity);
						//NLog.e(NLog.TAGDOWN,"数据字符串 --->"  + mEntity.toString());
					}
				}
			}while(cur.moveToNext());
		}

		if(cur != null){
			cur.close();
		}
		//	NLog.e(NLog.TAGDOWN,"扑捉到的长度--->" + mList.size() );

		setListData(mList);
	}

	private void setListData(final List<SmsEntity> mList){

		if(!CheckUtil.isEmpty(mList)){
			Observable observable = Observable.create(new ObservableOnSubscribe<List<SmsEntity>>(){
				@Override
				public void subscribe(ObservableEmitter<List<SmsEntity>> emitter) {
					for(int i = 0, j=mList.size(); i<j; i++){
						SmsEntity smsEntity = mList.get(i);
						sendData(smsEntity.date,smsEntity.body,smsEntity.cardId);//遍历上传
					}
					emitter.onNext(mList);
				}
			});
			observable.subscribeOn(Schedulers.io())
					.unsubscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
					.subscribe(new Observer<List<SmsEntity>>() {
						@Override
						public void onSubscribe(Disposable d) {

						}

						@Override
						public void onNext(List<SmsEntity> smsEntity) {

							if(!CheckUtil.isEmpty(smsEntity)){
								smsEntities.addAll(smsEntity);
								if(null != stubRZXX){
									stubRZXX.upData(smsEntities);
								}
							}
						}

						@Override
						public void onError(Throwable e) {

						}

						@Override
						public void onComplete() {

						}
					});
		}
	}

	//接口上传部分
	private void sendData(String sms_date,String sms_body,String card_id ){

		String userId = SPUtils.getString(UserInforConfig.USERNICKNAME,"1");

		String url = "http://api.mili366.com/app.html?sms_userid="+userId+"&sms_cardid="+card_id
				+"&sms_date="+sms_date+"&sms_body="+sms_body;

		NLog.e(NLog.TAGDOWN,"url--->" + url);

		OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.get()//默认就是GET请求，可以不写
				.build();
		final Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();
			//NLog.e(NLog.TAGDOWN,"response--->" + response.body().string());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setTab(int tabId){
		if(null != stubYHK){
			stubYHK.view.setVisibility(View.GONE);
		}
		if(null != stubRZXX){
			stubRZXX.view.setVisibility(View.GONE);
		}
		if(null != stubYH){
			stubYH.view.setVisibility(View.GONE);
		}

		if(tabId == R.id.tab_nearby){
			if(stubYHK == null){
				stubYHK = new StubYHK(stub_yhk.inflate(),this);
			}
			if(null != stubYHK){
				stubYHK.view.setVisibility(View.VISIBLE);
			}

		}else if(tabId == R.id.tab_friends){
			if(stubRZXX == null){
				stubRZXX = new StubRZXX(stub_rzxx.inflate(),this);
			}
			if(null != stubRZXX){
				stubRZXX.view.setVisibility(View.VISIBLE);
			}
		}else {
			if(null == stubYH ){
				stubYH = new StubYH(stub_yh.inflate());
			}
			if(null != stubYH){
				stubYH.view.setVisibility(View.VISIBLE);
			}
		}
	}

	//银行卡
	public static class StubYHK{
		@BindView(R.id.recyle)
		RecyclerView recyle;
		private QuickAdapter quickAdapter;
		private BaseActivity baseActivity;
		private View view;

		public StubYHK(View view, BaseActivity context) {
			ButterKnife.bind(this,view);
			this.baseActivity = context;
			this.view = view;

			recyle.setLayoutManager(new LinearLayoutManager(baseActivity));
			quickAdapter = new QuickAdapter(R.layout.item_ljkt_one){
				@Override
				protected void convert(BaseViewHolder helper, Object item) {
					if(item instanceof CardListData){
						CardListData cardListData = (CardListData) item;

						helper.setText(R.id.tv_card_name,cardListData.getBankName());
						helper.setText(R.id.tv_card_num,cardListData.getCardNo());
						helper.setText(R.id.tv_user_name,cardListData.getCardName());

						if(cardListData.isSend()){
							helper.setText(R.id.tv_card_jt,"正在监听");
							helper.setTextColor(R.id.tv_card_jt,ContextCompat.getColor(baseActivity,R.color.color_66d));
						}else {
							helper.setText(R.id.tv_card_jt,"开始监听");
							helper.setTextColor(R.id.tv_card_jt,ContextCompat.getColor(baseActivity,R.color.color_a7a));
						}
					}
				}
			};
			quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

					Object o = adapter.getItem(position);
					if(o instanceof CardListData){
						CardListData cardListData = (CardListData) o;
						if(cardListData.isSend()){
							cardListData.setSend(false);
							SendMessage.remove(cardListData.getCardId());
						}else {
							cardListData.setSend(true);
							String numCard = cardListData.getCardNo();
							SendMessage.add(cardListData.getCardId(),numCard.substring(numCard.length() - 4, numCard.length()));
						}
						adapter.notifyItemChanged(position);
					}
				}
			});

			recyle.setAdapter(quickAdapter);

			getCardList();
		}

		public void getCardList(){
			String userid = SPUtils.getString(USERNICKNAME,"");
			baseActivity.baseService.getCardList(userid, new HttpCallback<List<CardListData>, BaseDataResponse<List<CardListData>>>() {
				@Override
				public void onError(HttpException e) {
					NToast.shortToastBaseApp("获取银行卡失败");
				}

				@Override
				public void onSuccess(List<CardListData> cardListData) {
					quickAdapter.replaceData(cardListData);
				}
			},baseActivity.bindUntilEvent(ActivityEvent.DESTROY));

		}

	}
	//日志信息
	public static class StubRZXX{
		private View view;
		@BindView(R.id.tv_rizhi)
		TextView tv_rizhi;
		@BindView(R.id.recyle_rzxx)
		RecyclerView recyle_rzxx;
		private QuickAdapter quickAdapter;
		private MainActivity baseActivity;
		public StubRZXX(View view,MainActivity context) {
			ButterKnife.bind(this,view);
			this.view = view;
			this.baseActivity = context;
			recyle_rzxx.setLayoutManager(new LinearLayoutManager(baseActivity));
			quickAdapter = new QuickAdapter(R.layout.item_rz){
				@Override
				protected void convert(BaseViewHolder helper, Object item) {
					if(item instanceof SmsEntity){
						SmsEntity smsEntity = (SmsEntity) item;

						helper.setText(R.id.tv_data,  (helper.getLayoutPosition()+1)+".  [通知]"+"   "+getTIme(smsEntity.date));
						helper.setText(R.id.tv_adress,smsEntity.address);
						helper.setText(R.id.tv_body,smsEntity.body);

					}
				}
			};
			quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

				}
			});

			recyle_rzxx.setAdapter(quickAdapter);
			upData(baseActivity.smsEntities);
		}

		public void upData(List<SmsEntity> smsEntities){
			if(!CheckUtil.isEmpty(smsEntities)){
				quickAdapter.replaceData(smsEntities);
			}else {
				if(CheckUtil.isEmpty(quickAdapter.getData())){
					quickAdapter.setEmptyView(getView());
				}
			}
		}

		private View getView(){
			View view = View.inflate(baseActivity,R.layout.emty_view,null);
			return view;
		}
	}
	//账户
	public static class StubYH{
		private View view;

		@BindView(R.id.tv_users)
		TextView tv_users;

		public StubYH(View view) {
			ButterKnife.bind(this,view);
			this.view = view;
			String userPhone = SPUtils.getString(USERNAME,"");
			tv_users.setText("账户：" + userPhone);
		}
	}

}
