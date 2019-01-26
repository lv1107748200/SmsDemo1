package com.hxj.sms;

import java.util.Random;

public class SJCardID {
    private static Random random = new Random();
    private static String[] imgs = new String[]{
            "2775",
            "7141",
    };

    public static String getCaraNum() {
        return imgs[random.nextInt(imgs.length)];
    }
}
