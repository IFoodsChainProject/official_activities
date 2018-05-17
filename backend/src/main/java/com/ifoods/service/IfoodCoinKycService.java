package com.ifoods.service;

import com.ifoods.bean.common.activity.IfoodCoinKyc;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月13日
 */
public interface IfoodCoinKycService {

    void save(IfoodCoinKyc coinKyc);

    IfoodCoinKyc findAllByEmail(String email);
    
    IfoodCoinKyc findByEmailAndPwd(String email, String passwd);

    /**
     * 根据Email获得当前用户的信息
     * @param email
     */
    IfoodCoinKyc findByEmail(String email);
    
    int countByEmail(String email);
}
