package com.ifoods.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月19日
 */
public interface CacheTableMapper {

    /**
     * @param key
     * @param value
     * 保存缓存信息
     */
    void saveAndUpdate(@Param("key")String key, @Param("value")String value);

    /**
     * @param key
     * @return
     * 根据key 获得 value
     */
    String getCacheValueByKey(@Param("key")String key);

    /**
     * @param key
     * 
     * 根据key 删除缓存信息
     */
    void deleteByKey(@Param("key")String key);

}
