package com.example.demo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	Logger logger = LogManager.getLogger(RedisConfig.class);
	@Value("${spring.redis.host}")
	private String host="192.168.10.128";
	@Value("${spring.redis.port}")
	private int port=6379;
	@Value("${spring.redis.timeout}")
	private int timeout=10;
	@Value("${spring.redis.jedis.pool.max-active}")
	private int poolMaxActive=100;
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int poolMaxIdle=50;
	@Value("${spring.redis.jedis.pool.max-wait}")
	private int poolMaxWait=50;

	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(poolMaxIdle);
		// jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setMaxTotal(poolMaxActive);
		// jedisPoolConfig.setMinIdle(minIdle);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
		logger.info("JedisPool注入成功！");
		logger.info("redis地址：" + host + ":" + port);
		return jedisPool;
	}
}
