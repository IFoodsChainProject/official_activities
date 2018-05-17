package com.ifoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifoods.bean.common.activity.Activity0505;
import com.ifoods.bean.common.activity.CandyActivity;
import com.ifoods.dao.CandyActivityMapper;
import com.ifoods.service.CandyActivityService;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
@Service
public class CandyActivityServiceImpl implements CandyActivityService {

    @Autowired
    private CandyActivityMapper candyActivityMapper;
    
    @Override
    public void save(CandyActivity candyActivity) {
        candyActivityMapper.save(candyActivity);
    }

    @Override
    public List<CandyActivity> findAllByEmail(String email) {

        return candyActivityMapper.findAllByEmail(email);
    }

    @Override
    public void save(Activity0505 activity0505) {
        candyActivityMapper.save0505(activity0505);
    }
    
    @Override
    public List<Activity0505> findAllByEmail0505(String email) {

        return candyActivityMapper.findAllByEmail0505(email);
    }

}
