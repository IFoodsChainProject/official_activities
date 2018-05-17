package com.ifoods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ifoods.bean.common.activity.IfoodCoinKyc;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月19日
 */
public interface IfoodCoinKycMapper {

    /**
     * @param coinKyc
     * 保存 kyc 信息 
     */
    void save(IfoodCoinKyc coinKyc);

    /**
     * @param email
     * @param passwd
     * @return
     * 根据用户名和密码返回 一条 kyc 信息
     */
    IfoodCoinKyc findByEmailAndPassword(@Param("email")String email, @Param("password")String passwd);

    /**
     * @param email
     * @return
     * 
     * 返回当前 邮箱 的信息, 邮箱唯一索引
     */
    IfoodCoinKyc findByEmail(String email);

    /**
     * @param email
     * @return
     * 获得当前邮箱下的 kyc 信息数量
     */
    Long countByEmail(String email);
    
    

}
