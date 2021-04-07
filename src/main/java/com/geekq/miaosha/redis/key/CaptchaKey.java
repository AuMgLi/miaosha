package com.geekq.miaosha.redis.key;

public class CaptchaKey extends BasePrefix {

    private static final String PREFIX = "token:";

    public CaptchaKey() {
        super(PREFIX);
    }

    public CaptchaKey(int expireSeconds) {
        super(expireSeconds, PREFIX);
    }

}
