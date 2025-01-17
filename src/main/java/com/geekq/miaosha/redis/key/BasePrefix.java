package com.geekq.miaosha.redis.key;

import com.geekq.miaosha.redis.key.KeyPrefix;

public class BasePrefix implements KeyPrefix {

    private final int expireSeconds;
    private final String prefix ;

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
       this(0, prefix);
    }

    @Override
    public int expireSeconds() {  //默认0代表永远不过期
        return expireSeconds;
    }

    /**
     * 可确定获取唯一key
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
