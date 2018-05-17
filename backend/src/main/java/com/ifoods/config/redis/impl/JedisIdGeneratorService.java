package com.ifoods.config.redis.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifoods.config.redis.IdGeneratorService;
import com.ifoods.config.redis.RedisClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JedisIdGeneratorService implements IdGeneratorService {
	

	@Autowired
	private RedisClient jedisClient;

	private String getOrderPrefix(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int day = c.get(Calendar.DAY_OF_YEAR); // 今天是第多少天
		int hour =  c.get(Calendar.HOUR_OF_DAY);
		String dayFmt = String.format("%1$03d", day); // 0补位操作 必须满足三位
		String hourFmt = String.format("%1$02d", hour);  // 0补位操作 必须满足2位
		StringBuffer prefix = new StringBuffer();
		prefix.append((year - 2000)).append(dayFmt).append(hourFmt);
		return prefix.toString();
	}
	

	public Long incrOrderId(String biz, String prefix) {
		String orderId = null;
		String key = "supplychain:#{biz}:id:".replace("#{biz}", biz).concat(prefix);
		try {
			Long index = jedisClient.incr(key); 
			orderId = prefix.split(":")[1].concat(String.format("%1$04d",index)); // 补位操作 保证满足4位
		} catch(Exception ex) {
           log.error("生成单号发生异常 {}",ex.getMessage());
		}
		return Long.parseLong(orderId);
	} 

	@Override
	public Long generatorId(String biz) {
		return incrOrderId(biz, getOrderPrefix(new Date()));
	}
}
