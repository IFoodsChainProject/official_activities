package com.ifoods;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifoods.config.redis.impl.JedisIdGeneratorService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zhenghui.li
 * @date 2018年5月13日
 */
@RunWith(SpringRunner.class) //14.版本之前用的是SpringJUnit4ClassRunner.class
@SpringBootTest(classes = Application.class)
@Slf4j
@Component
public class RedisTest {

    @Autowired
    private static StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisTest.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * ID生成器
     */
    private JedisIdGeneratorService jedisIdGeneratorService;

    @Test
    public void testRadis() throws Exception {

        stringRedisTemplate.opsForValue().set("622688orgCode", "14144500");

        String orgCode = stringRedisTemplate.boundValueOps("622688orgCode").get();

        Assert.assertTrue("14144500".equals(orgCode));
    }

    @Test
    public void testGeneratorId() throws Exception {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            cachedThreadPool.execute(new Runnable() {
//                public void run() {
//                    while (true)
                        System.out.println(jedisIdGeneratorService.incrOrderId("bill","1473:20171116"));
//                }
//            });
        //}
        //System.in.read();
    }
}
