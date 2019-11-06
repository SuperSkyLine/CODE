package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RedisUtil;
import com.example.demo.service.user.UserService;

import redis.clients.jedis.JedisPool;



@SpringBootTest
class DemoApplicationTests {
	@Autowired
	UserService userService;
	@Autowired
	JedisPool jedisPool;
	@Test
	void contextLoads() {
		RedisUtil.setV("user", userService.getUserInfo(), 0,jedisPool);
		RedisUtil.delV("user",0, jedisPool);
		System.out.println(JSON.toJSONString(RedisUtil.getVStr("user",0,jedisPool)));
	}

}
