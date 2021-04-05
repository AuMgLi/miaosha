package com.geekq.miaosha.redis.redismanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * lua脚本使用
 */
public class RedisLua {

    private static final Logger logger = LoggerFactory.getLogger(RedisLua.class);

    /**
     * 未完成  需 evalsha更方便 限制ip 或者 手机号访问次数
     */
    public static void getLuaLimit() {

        Jedis jedis = null;
        try {
            jedis = RedisManager.getJedis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lua =
                "local num=redis.call('incr',KEYS[1]) if tonumber(num)==1 " +
                        "then redis.call('expire',KEYS[1],ARGV[1]) " +
                        "return 1 elseif tonumber(num)>" +
                        "tonumber(ARGV[2]) then return 0 else return 1 end";

        List<String> keys = new ArrayList<>();
        keys.add("ip:limit:127.0.0.1");
        List<String> args = new ArrayList<>();
        args.add("6000");
        args.add("5");
//        jedis.auth("xxxx");
//        Object evalSha = jedis.evalsha(lua);
        String luaScript = jedis.scriptLoad(lua);
        System.out.println(luaScript);
        Object object = jedis.evalsha(luaScript, keys, args);
        System.out.println(object);
    }

    /**
     * 统计访问次数
     */
    public static Object getVisitorCount(String key) {
        Jedis jedis;
        Object object;
        try {
            jedis = RedisManager.getJedis();

        String count =
                "local num=redis.call('get',KEYS[1]) return num";
        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> argv = new ArrayList<>();
//        jedis.auth("youxin11");
        String luaScript = jedis.scriptLoad(count);
        logger.info("luaScript:" + luaScript);
        object = jedis.evalsha(luaScript, keys, argv);
        } catch (Exception e) {
            logger.error("统计访问次数失败！！！",e);
            return "0";
        }
        return object;
    }

    /**
     * 统计访问次数
     */
    public static void visitorCount(String key) {
        Jedis jedis;
        try {
            jedis = RedisManager.getJedis();
            String count =
                    "local num=redis.call('incr',KEYS[1]) return num";
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
//            jedis.auth("youxin11");
            String luaScript = jedis.scriptLoad(count);
            logger.info("luaScript:" + luaScript);
            jedis.evalsha(luaScript, keys, args);
        } catch (Exception e) {
            logger.error("统计访问次数失败！！！", e);
        }
    }
}
