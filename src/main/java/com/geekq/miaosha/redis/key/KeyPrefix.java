package com.geekq.miaosha.redis.key;

public interface KeyPrefix {

    int expireSeconds() ;
    String getPrefix() ;

}
