package com.ifoods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifoods.bean.common.activity.IfoodCoinKyc;
import com.ifoods.dao.IfoodCoinKycMapper;
import com.ifoods.service.IfoodCoinKycService;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月13日
 */
@Service
public class IfoodCoinKycServiceImpl implements IfoodCoinKycService {

    @Autowired
    private IfoodCoinKycMapper ifoodCoinKycMapper;
    
    @Override
    public void save(IfoodCoinKyc coinKyc) {
        ifoodCoinKycMapper.save(coinKyc);
    }

    @Override
    public IfoodCoinKyc findAllByEmail(String email) {
        
        return ifoodCoinKycMapper.findByEmail(email);
    }

    @Override
    public IfoodCoinKyc findByEmailAndPwd(String email, String passwd) {

        return ifoodCoinKycMapper.findByEmailAndPassword(email, passwd);
    }

    @Override
    public IfoodCoinKyc findByEmail(String email) {
        return ifoodCoinKycMapper.findByEmail(email);
    }

    @Override
    public int countByEmail(String email) {
        Long count = ifoodCoinKycMapper.countByEmail(email);
        if(count == null) {
            return 0;
        }
        return count.intValue();
    }

}
