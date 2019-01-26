package com.hxj.sms;

import com.xxbm.sbecomlibrary.utils.NLog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SendMessage {
    volatile private static SendMessage instance = null;

    volatile private static Map<String,String> stringStringMap = new HashMap<>();

    public static SendMessage getInstance() {
        if(instance == null){
            synchronized (SendMessage.class) {
                if(instance == null){
                    instance = new SendMessage();
                }
            }
        }
        return instance;
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
            stringStringMap = new HashMap<>();
        }
        return stringStringMap;
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
}
