package com.xxbm.sbecomlibrary.net.entry.response;


import android.os.Parcel;
import android.os.Parcelable;

/*
 * lv   2019/1/23
 */
public class CardListData implements Parcelable {

    /**
     * CardId : 1
     * CardName : 陈富勇
     * BankName : 中国建设银行
     * CardNo : 6217002430031072565
     */

    private String CardId;
    private String CardName;
    private String BankName;
    private String CardNo;
    private boolean isSend;

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String CardId) {
        this.CardId = CardId;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CardId);
        dest.writeString(this.CardName);
        dest.writeString(this.BankName);
        dest.writeString(this.CardNo);
    }

    public CardListData() {
    }

    protected CardListData(Parcel in) {
        this.CardId = in.readString();
        this.CardName = in.readString();
        this.BankName = in.readString();
        this.CardNo = in.readString();
    }

    public static final Parcelable.Creator<CardListData> CREATOR = new Parcelable.Creator<CardListData>() {
        @Override
        public CardListData createFromParcel(Parcel source) {
            return new CardListData(source);
        }

        @Override
        public CardListData[] newArray(int size) {
            return new CardListData[size];
        }
    };
}
