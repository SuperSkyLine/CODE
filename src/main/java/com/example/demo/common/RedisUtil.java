package com.example.demo.common;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Component
public class RedisUtil {
	static Logger log = LogManager.getLogger(RedisUtil.class);

	public final static String DATA_REDIS_KEY = "data";
	
	static JedisPool jedisPool;
	@Autowired
	JedisPool jedisPoolConfig;
	@PostConstruct //依赖注入完成后调用
	private void init() {
		jedisPool=jedisPoolConfig;
	}

	/**
	 * 
	 * setVExpire(设置key值，同时设置失效时间 秒) @Title: setVExpire @param @param
	 * key @param @param value @param @param seconds @param index 具体数据库
	 * 默认使用0号库 @return void @throws
	 */
	public static <V> void setVExpire(String key, V value, int seconds, int index ) {
		String json = JSON.toJSONString(value);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.set(DATA_REDIS_KEY + key, json);
			jedis.expire(DATA_REDIS_KEY + key, seconds);
		} catch (Exception e) {
			log.error("setV初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}

	}

	/**
	 * 
	 * (存入redis数据) @Title: setV @param @param key @param @param value @param index
	 * 具体数据库 @return void @throws
	 */
	public static <V> void setV(String key, V value, int index ) {
		String json = JSON.toJSONString(value);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.set(DATA_REDIS_KEY + key, json);
		} catch (Exception e) {
			log.error("setV初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}

	}

	/*
	 * 刪除key
	 */
	public static void delV(String key, int index) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.del(DATA_REDIS_KEY + key);
		} catch (Exception e) {
			log.error("delV刪除失败" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 
	 * getV(获取redis数据信息) @Title: getV @param @param key @param index 具体数据库 0:常用数据存储
	 * 3：session数据存储 @param @return @return V @throws
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getV(String key, int index ) {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			value = jedis.get(DATA_REDIS_KEY + key);
		} catch (Exception e) {
			log.error("getV初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
		return (V) JSONObject.parse(value);
	}

	/**
	 * 
	 * getVString(返回json字符串) @Title: getVString @param @param key @param @param
	 * index @param @return @return String @throws
	 */
	public static String getVStr(String key, int index ) {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			value = jedis.get(DATA_REDIS_KEY + key);
		} catch (Exception e) {
			log.error("getVString初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
		return value;
	}

	/**
	 * 
	 * Push(存入 数据到队列中)
	 * 
	 * @Title: Push @param @param key @param @param value @return void @throws
	 */
	public static <V> void Push(String key, V value ) {
		String json = JSON.toJSONString(value);
		Jedis jedis = null;
		try {
			log.info("存入 数据到队列中");
			jedis = jedisPool.getResource();
			jedis.select(15);
			jedis.lpush(key, json);
		} catch (Exception e) {
			log.error("Push初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 
	 * Push(存入 数据到队列中)
	 * 
	 * @Title: PushV @param key @param value @param dBNum @return void @throws
	 */
	public static <V> void PushV(String key, V value, int dBNum ) {
		String json = JSON.toJSONString(value);
		Jedis jedis = null;
		try {
			log.info("存入 数据到队列中");
			jedis = jedisPool.getResource();
			jedis.select(dBNum);
			jedis.lpush(key, json);
		} catch (Exception e) {
			log.error("Push初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 
	 * Push(存入 数据到队列中)
	 * 
	 * @Title: Push @param @param key @param @param value @return void @throws
	 */
	public static <V> void PushEmail(String key, V value ) {

		String json = JSON.toJSONString(value);
		Jedis jedis = null;
		try {
			log.info("存入 数据到队列中");
			jedis = jedisPool.getResource();
			jedis.select(15);
			jedis.lpush(key, json);
		} catch (Exception e) {
			log.error("Push初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 
	 * Pop(从队列中取值)
	 * 
	 * @Title: Pop @param @param key @param @return @return V @throws
	 */
	@SuppressWarnings("unchecked")
	public static <V> V Pop(String key ) {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(15);
			value = jedis.rpop(DATA_REDIS_KEY + key);
		} catch (Exception e) {
			log.error("Pop初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}
		return (V) value;
	}

	/**
	 * 
	 * expireKey(限时存入redis服务器)
	 * 
	 * @Title: expireKey @param @param key @param @param seconds @return
	 *         void @throws
	 */
	public static void expireKey(String key, int seconds ) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(3);
			jedis.expire(DATA_REDIS_KEY + key, seconds);
		} catch (Exception e) {
			log.error("Pop初始化jedis异常：" + e);
			if (jedis != null) {
				jedis.close();
			}
		} finally {
			closeJedis(jedis);
		}

	}

	/**
	 * 
	 * closeJedis(释放redis资源)
	 * 
	 * @Title: closeJedis @param @param jedis @return void @throws
	 */
	public static void closeJedis(Jedis jedis) {
		try {
			if (jedis != null) {
				jedis.close();
			}
		} catch (Exception e) {
			log.error("释放资源异常：" + e);
		}
	}
}
