package com.yaoxj.controller;



import com.yaoxj.redis.mq.JedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;



@Slf4j
@RestController
public class GoodsController {



    @RequestMapping("/goodsExe")
	public String orderList(){
		Jedis jedis = JedisPoolUtils.getJedis();
		String appKey="";
		String s = jedis.get(appKey);

		return "";
	}



}
