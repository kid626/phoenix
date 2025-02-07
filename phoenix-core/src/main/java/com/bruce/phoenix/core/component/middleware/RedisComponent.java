package com.bruce.phoenix.core.component.middleware;

import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.common.lock.Action;
import com.bruce.phoenix.common.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2021/10/13 9:17
 * @Author fzh
 */
@Component
@Slf4j
public class RedisComponent implements Lock {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Value("${spring.redis.prefix:}")
    public String prefix;

    private static final long DEFAULT_VALUE = 1;
    private static final long DEFAULT_TIMEOUT = 1;
    private static final TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
    /**
     * 加锁标志
     */
    public static final String LOCKED = "LOCKED";


    /**
     * 判断是否存在这个键
     *
     * @param key 键名
     * @return 存在则返回 true，否则返回 false
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    /**
     * 根据前缀获取键
     *
     * @param prefix 前缀，支持 *
     * @return 该前缀查询出来的所有 key
     */
    public Set<String> getKeys(String prefix) {
        return redisTemplate.keys(getKey(prefix));
    }


    /**
     * 删除一个键
     *
     * @param key 键名
     */
    public void deleteKey(String key) {
        redisTemplate.delete(getKey(key));
    }

    /**
     * 为设置的键值对设置有效期，有效期默认为 1000 毫秒
     *
     * @param key   键名
     * @param value 值
     */
    public void setExpire(String key, String value) {
        setExpire(key, value, DEFAULT_TIMEOUT, DEFAULT_UNIT);
    }

    /**
     * 为设置的键值对设置有效期
     *
     * @param key      键名
     * @param value    值
     * @param timeout  时间
     * @param timeUnit 时间粒度
     */
    public void setExpire(String key, String value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(getKey(key), value, timeout, timeUnit);
    }

    /**
     * 返回数据类型
     *
     * @param key 键名
     * @return 数据类型
     * NONE("none"),
     * STRING("string"),
     * LIST("list"),
     * SET("set"),
     * ZSET("zset"),
     * HASH("hash");
     */
    public String getType(String key) {
        return String.valueOf(redisTemplate.type(getKey(key)));
    }

    /**
     * set
     *
     * @param key   键名称
     * @param value 值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(getKey(key), value);
    }

    /**
     * 值增加减少
     *
     * @param key 键名
     * @param num 正数为加，负数为减
     */
    public Long incr(String key, Integer num) {
        return redisTemplate.opsForValue().increment(getKey(key), num);
    }

    /**
     * get
     *
     * @param key 键名称
     * @return 对应的值
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(getKey(key));
    }

    //redisTemplate.opsForHash()操作Hash表

    /**
     * hset     设置键值对
     *
     * @param hashName 哈希表名
     * @param key      键名称
     * @param value    值
     */
    public void hSet(String hashName, String key, String value) {
        redisTemplate.opsForHash().put(getKey(hashName), key, value);
    }

    /**
     * hmset    批量设置
     *
     * @param hashName 哈希表名
     * @param map      存放多个键值对
     */
    public void hmSet(String hashName, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(getKey(hashName), map);
    }

    /**
     * hdel 删除
     *
     * @param hashName 哈希表名
     * @param keys     可以同时删除多个 getKey
     */
    public void hDel(String hashName, String... keys) {
        redisTemplate.opsForHash().delete(getKey(hashName), keys);
    }

    /**
     * 判断键是否存在
     *
     * @param hashName 哈希表名
     * @param key      键名称
     * @return 键是否存在，存在返回 true，否则返回 false
     */
    public Boolean hasHashKey(String hashName, String key) {
        return redisTemplate.opsForHash().hasKey(getKey(hashName), key);
    }

    /**
     * hget    获取某个键值对
     *
     * @param hashName 哈希表名
     * @param key      键名称
     * @return 值
     */
    public String hGet(String hashName, String key) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(getKey(hashName), key);
    }

    /**
     * 判断 hashName 是否存在
     *
     * @param hashName 哈希表名
     * @return 值
     */
    public boolean hExist(String hashName) {
        return redisTemplate.opsForHash().size(getKey(hashName)) > 0;
    }


    /**
     * 根据 key 获取对应 hash 表的所有键值
     *
     * @param hashName 哈希表名
     * @return 值
     */
    public Set<String> hKeys(String hashName) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        return opsForHash.keys(getKey(hashName));
    }

    /**
     * 移除有序集合中，区间内的成员
     *
     * @param key 集合名
     * @param min 最小
     * @param max 最大
     */
    public void removeRangeByScore(String key, double min, double max) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.removeRangeByScore(getKey(key), min, max);
    }

    /**
     * 集合内元素总数
     *
     * @param key 集合名
     * @param min 最小
     * @param max 最大
     * @return 元素总数
     */
    public Long count(String key, double min, double max) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        return zSet.count(getKey(key), min, max);
    }

    /**
     * 加入集合
     *
     * @param key   集合名
     * @param value 值
     * @param score 分数
     */
    public void add(String key, String value, double score) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.add(getKey(key), value, score);
    }

    /**
     * 插入列表头部
     *
     * @param key   列表名
     * @param value 值
     */
    public Long leftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(getKey(key), value);
    }

    /**
     * 批量插入列表头部
     *
     * @param key    列表名
     * @param values 值
     */
    public Long leftPushAll(String key, List<String> values) {
        return redisTemplate.opsForList().leftPushAll(getKey(key), values);
    }

    /**
     * 插入列表尾部
     *
     * @param key   列表名
     * @param value 值
     */
    public Long rightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(getKey(key), value);
    }

    /**
     * 批量插入列表尾部
     *
     * @param key    列表名
     * @param values 值
     */
    public Long rightPushAll(String key, List<String> values) {
        return redisTemplate.opsForList().rightPushAll(getKey(key), values);
    }

    /**
     * 移除列表第一个元素并返回
     *
     * @param key 列表名
     */
    public String leftPop(String key) {
        return redisTemplate.opsForList().leftPop(getKey(key));
    }

    /**
     * 移除列表最后一个元素并返回
     *
     * @param key 列表名
     */
    public String rightPop(String key) {
        return redisTemplate.opsForList().rightPop(getKey(key));
    }

    /**
     * 获取列表长度
     *
     * @param key 列表名
     */
    public Long size(String key) {
        return redisTemplate.opsForList().size(getKey(key));
    }

    /**
     * 根据下标获取列表元素
     *
     * @param key   列表名
     * @param start 开始索引
     * @param end   结束索引
     */
    public List<String> range(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(getKey(key), start, end);
    }

    /**
     * 获取指定下标索引的列表元素
     *
     * @param key   列表名
     * @param index 下标索引
     */
    public String index(String key, Long index) {
        return redisTemplate.opsForList().index(getKey(key), index);
    }

    /**
     * 集合内是否包含值
     *
     * @param key   集合名称
     * @param value 值
     */
    public Boolean isMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(getKey(key), value);
    }

    /**
     * 集合里添加成员
     *
     * @param key   集合名称
     * @param value 值
     */
    public void setAdd(String key, String value) {
        redisTemplate.opsForSet().add(getKey(key), value);
    }

    /**
     * 获取集合内所有元素
     *
     * @param key 集合名称
     * @return 集合内所有元素
     */
    public Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(getKey(key));
    }

    /**
     * 集合元素删除
     *
     * @param key   集合名称
     * @param value 元素值
     */
    public void setRemove(String key, String value) {
        redisTemplate.opsForSet().remove(getKey(key), value);
    }


    /**
     * 一次删除多个key
     *
     * @param set
     */
    public void deleteKeys(Collection<String> set) {
        for (String key : set) {
            deleteKey(key);
        }
    }

    /**
     * 获取并删除（并不支持 pipeline 的方式）
     *
     * @param key
     * @return
     */
    public String getAndDelete(String key) {
        String result = get(key);
        deleteKey(key);
        return result;
    }

    /**
     * 对某个 key 的值 +value 并延长 key 的 ttl
     *
     * @param key    键
     * @param value  增加值
     * @param expire 秒
     */
    public Long incrAndExpire(String key, long value, long expire) {
        List<Object> result = executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = getKey(key).getBytes();
                connection.incrBy(bytes, value);
                connection.expire(bytes, expire);
                // 必须返回 null
                return null;
            }
        });
        return Long.valueOf(result.get(0) + "");
    }

    public Long incrAndExpire(String key, long expire) {
        return incrAndExpire(key, DEFAULT_VALUE, expire);
    }

    public Long incrAndExpire(String key) {
        return incrAndExpire(key, DEFAULT_VALUE, DEFAULT_TIMEOUT);
    }

    /**
     * 用于延迟 expire 时间
     *
     * @param key      key 值
     * @param timeout  延长时间
     * @param timeUnit 单位
     */
    public void expire(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(getKey(key), timeout, timeUnit);
    }

    /**
     * 获得锁
     * 插入成功返回 true，否则表明已经上锁
     *
     * @param key 锁名称
     */
    public boolean generateLock(String key) {
        return generateLock(key, DEFAULT_TIMEOUT);
    }

    /**
     * 获得锁
     * 插入成功返回 true，否则表明已经上锁
     *
     * @param key     锁名称
     * @param seconds 秒
     */
    public boolean generateLock(String key, long seconds) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(getKey(key), LOCKED, seconds, DEFAULT_UNIT);
        return success != null && success;
    }

    /**
     * 释放锁
     *
     * @param lockName 锁名称
     */
    public void releaseLock(String lockName) {
        deleteKey(lockName);
    }


    public List<Object> executePipelined(RedisCallback<?> action) {
        return redisTemplate.executePipelined(action);
    }

    @Override
    public <T> T lock4Run(String lockKey, Action<T> action) {
        boolean locked;
        try {
            locked = generateLock(lockKey, 10);
        } catch (Exception e) {
            // 降级处理, 避免redis 挂了导致获取不到锁
            locked = true;
            log.error("获取锁资源失败,lockKey={}", lockKey);
        }
        if (locked) {
            try {
                return action.run();
            } finally {
                releaseLock(lockKey);
            }
        } else {
            log.error("获取锁资源失败,lockKey={}", lockKey);
            throw new RuntimeException("获取锁资源失败,lockKey=" + lockKey);
        }
    }

    @Override
    public <T> T lock4Run(String lockKey, Action<T> action, int retryNum) {
        for (int i = 0; i < retryNum; i++) {
            boolean locked;
            try {
                locked = generateLock(lockKey, 10);
            } catch (Exception e) {
                // 降级处理, 避免redis 挂了导致获取不到锁
                locked = true;
                log.error("获取锁资源失败,lockKey={}", lockKey);
            }
            if (locked) {
                try {
                    return action.run();
                } finally {
                    releaseLock(lockKey);
                }
            } else {
                log.error("获取锁资源失败,lockKey={}", lockKey);
            }
        }
        log.error("尝试" + retryNum + "次，分布式锁均失败");
        //正常，不可能执行到这里
        throw new RuntimeException("尝试" + retryNum + "次，分布式锁均失败,lockKey=" + lockKey);
    }


    public void send(String channel, String data) {
        redisTemplate.convertAndSend(getKey(channel), data);
    }

    /**
     * 获取 key 值
     *
     * @param key redis 的 key
     * @return 添加项目前缀
     */
    private String getKey(String key) {
        if (StrUtil.isNotBlank(this.prefix)) {
            return this.prefix + ":" + key;
        }
        return key;
    }


}
