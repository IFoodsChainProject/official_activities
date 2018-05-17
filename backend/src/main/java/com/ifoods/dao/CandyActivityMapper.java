package com.ifoods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ifoods.bean.common.activity.Activity0505;
import com.ifoods.bean.common.activity.CandyActivity;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
public interface CandyActivityMapper {

    /**
     * @param candyActivity
     */
    void save(CandyActivity candyActivity);

    /**
     * @param email
     * @return
     */
    List<CandyActivity> findAllByEmail(String email);

    /**
     * @param activity0505
     * @return
     */
    void save0505(Activity0505 activity0505);
    
    
    List<Activity0505> findAllByEmail0505(String email);

}
