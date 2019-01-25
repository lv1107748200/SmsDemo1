package com.hxj.sms;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hxj.sms.adapter.QuickAdapter;
import com.hxj.sms.base.BaseExitActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xxbm.sbecomlibrary.base.activity.BaseActivity;
import com.xxbm.sbecomlibrary.net.base.BaseDataResponse;
import com.xxbm.sbecomlibrary.net.entry.response.CardListData;
import com.xxbm.sbecomlibrary.net.http.HttpCallback;
import com.xxbm.sbecomlibrary.net.http.HttpException;
import com.xxbm.sbecomlibrary.utils.NToast;

import java.util.List;

import butterknife.BindView;

/*
 * lv   2019/1/23
 */
public class CardActivity extends BaseExitActivity {
    public static final String  CARDTYPE = "CardActivity";
    private QuickAdapter quickAdapter;

    @BindView(R.id.recyle)
     RecyclerView recyle;


    @Override
    public int getLayout() {
        return R.layout.activity_card;
    }

    @Override
    public void initView() {
        super.initView();
        recyle.setLayoutManager(new LinearLayoutManager(this));
        quickAdapter = new QuickAdapter(R.layout.item_ljkt_one){
            @Override
            protected void convert(BaseViewHolder helper, Object item) {
                if(item instanceof CardListData){
                    CardListData cardListData = (CardListData) item;

                    helper.setText(R.id.tv_card_name,cardListData.getBankName());
                    helper.setText(R.id.tv_card_num,cardListData.getCardNo());
                    helper.setText(R.id.tv_user_name,cardListData.getCardName());
                }
            }
        };
        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(CardActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.CARDTYPE,(CardListData)(adapter.getItem(position)));
                startActivity(intent);

            }
        });

        recyle.setAdapter(quickAdapter);
    }

    @Override
    public void initData(Intent intent) {
        super.initData(intent);

        String userid = intent.getStringExtra(CARDTYPE);
        getCardList(userid);

    }

    private void getCardList(String userid){
        baseService.getCardList(userid, new HttpCallback<List<CardListData>, BaseDataResponse<List<CardListData>>>() {
            @Override
            public void onError(HttpException e) {
                NToast.shortToastBaseApp("重新登陆");
            }

            @Override
            public void onSuccess(List<CardListData> cardListData) {
                quickAdapter.replaceData(cardListData);
            }
        },this.bindUntilEvent(ActivityEvent.DESTROY));

    }
}
