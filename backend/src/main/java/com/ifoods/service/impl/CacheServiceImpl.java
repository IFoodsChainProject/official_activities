package com.ifoods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifoods.dao.CacheTableMapper;
import com.ifoods.service.CacheService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李旗 on 2018/4/5 0005
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheTableMapper cacheTableMapper;


    @Override
    public void saveAndUpdateCache(String key, String value) {
        cacheTableMapper.saveAndUpdate(key, value);
    }

    @Override
    public String getCache(String key) {
        return cacheTableMapper.getCacheValueByKey(key);
    }

    @Override
    public void delete(String key) {
//        cacheTableRepository.delete(key);
        log.info("delete cache_table key:{}", key);
        cacheTableMapper.deleteByKey(key);
    }

    /*@Override
    public void cleanTimer(Date longer) {
        cacheTableRepository.deleteCacheTablesByCreateTimeBefore(longer);
        cacheTableMapper.cleanTimer(longer);
    }*/

    /*@Override
    public int countCacheByKeyAndValue(String key, String value) {
        Long count = cacheTableRepository.countCacheByKeyAndValue(key, value);
        if(count == null) {
            return 0;
        }
        return count.intValue();
        return 0;
    }*/
}
