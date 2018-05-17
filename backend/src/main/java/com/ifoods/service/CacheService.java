package com.ifoods.service;

/**
 * 
 */
public interface CacheService {

    void saveAndUpdateCache(String Key, String value);

    String getCache(String Key);

    void delete(String key);

    /*void cleanTimer(Date longer);
    
    int countCacheByKeyAndValue(String Key, String value);*/
}
