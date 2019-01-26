package com.hxj.sms;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.xxbm.sbecomlibrary.utils.NLog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SendMessage {
    volatile private static SendMessage instance = null;

     private static Map<String,String> stringStringMap = Collections
            .synchronizedMap(new HashMap<String,String>());
     private static Map<String,SMSHHHH> shijiachuo = Collections
            .synchronizedMap(new HashMap<String,SMSHHHH>());

    private static HandlerThread mWorkThread;
    private static Handler workHandler;

    public static SendMessage getInstance() {
        if(instance == null){
            synchronized (SendMessage.class) {
                if(instance == null){
                    instance = new SendMessage();
                    init();
                }
            }
        }
        return instance;
    }

    public static void init(){
        mWorkThread = new HandlerThread("CacheManger");
        mWorkThread.start();
        workHandler = new Handler(mWorkThread.getLooper());
    }

    public static Handler getWorkHandler (){
        if(null == workHandler)
            init();

        return workHandler;
    }

    public static void add(String cardId,String card){
        NLog.e(NLog.TAGOther,"--->"+"cardId = "+cardId+" card = " + card);
        getMap().put(cardId,card);
    }
    public static void remove(String cardId){
        NLog.e(NLog.TAGOther,"cardId--->"+cardId);
        getMap().remove(cardId);
    }

    public static CardAndWhat baohan(String boby){
        Iterator iter = getMap().entrySet().iterator();

        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
           String integ = (String) entry.getValue();
           String id = (String) entry.getKey();

           if(boby.indexOf(integ)!=-1){
               return new CardAndWhat(id,true);
           }
        }
        return new CardAndWhat("jj",false);
    }

    private static Map<String,String> getMap(){
        if(null == stringStringMap){
            stringStringMap = Collections
                    .synchronizedMap(new HashMap<String,String>());
        }
        return stringStringMap;
    }

    private static Map<String,SMSHHHH> getMapshijian(){
        if(null == shijiachuo){
            shijiachuo = Collections
                    .synchronizedMap(new HashMap<String,SMSHHHH>());
        }
        return shijiachuo;
    }


    public static class CardAndWhat{
        private String id;
        private boolean is;

        public CardAndWhat(String id, boolean is) {
            this.id = id;
            this.is = is;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIs() {
            return is;
        }

        public void setIs(boolean is) {
            this.is = is;
        }
    }

    //是否可以上传
    public static boolean getShangchuan(String adressss, SMSHHHH shijian){

        NLog.e(NLog.TAGDOWN,"判断上传传入值 --->" + "adressss  = "+ adressss
                + " shijian = " + shijian.time+ " id = " + shijian.Id );

        SMSHHHH smshhhh = getMapshijian().get(adressss);
        if(null != smshhhh){
            NLog.e(NLog.TAGDOWN,"判断上传MAP值 --->" + "adressss  = "+ adressss
                    + " shijian = " + smshhhh.time+ " id = " + smshhhh.Id );
        }

        boolean isWhat = false;


            if(null == smshhhh){
                getMapshijian().put(adressss,shijian);
                isWhat = true;
            }else {
                Long shjjjj = smshhhh.time;
                int id = smshhhh.Id;

                if(shjjjj < shijian.time){
                    getMapshijian().put(adressss,shijian);
                    isWhat = true;
                }else if(shjjjj == shijian.time){
                    if(id == shijian.Id){
                        isWhat = false;
                    }else {
                        getMapshijian().put(adressss,shijian);
                        isWhat = true;
                    }
                }else {
                    isWhat = false;
                }
            }

            NLog.e(NLog.TAGDOWN,"是否可以上传 --->" + isWhat );

            return isWhat;
    }


    public static class SMSHHHH{

        public SMSHHHH(Long time, int id) {
            this.time = time;
            Id = id;
        }

        private Long time;
        private int Id;

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }
    }
}
