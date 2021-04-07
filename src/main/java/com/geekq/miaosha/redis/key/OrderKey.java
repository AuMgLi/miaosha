package com.geekq.miaosha.redis.key;

public class OrderKey extends BasePrefix {

    public OrderKey( String prefix) {
        super( prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
