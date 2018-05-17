package com.ifoods.service;

import java.util.List;

import com.ifoods.bean.common.activity.Activity0505;
import com.ifoods.bean.common.activity.CandyActivity;

/**
 * 
 * @author zhenghui.li
 * @date 2018年4月17日
 */
public interface CandyActivityService {

    /**
     * 保存提交的信息
     * @param name
     * @param email
     * @param telephone
     * @param ethAddress
     */
    void save(CandyActivity candyActivity);

    /**
     * 根据 邮箱查询所有信息
     * @param email
     * @return
     */
    List<CandyActivity> findAllByEmail(String email);

    /**
     * @param activity0505
     * 保存信息
     */
    void save(Activity0505 activity0505);

    /**
     * 根据 邮箱查询所有信息
     * @param email
     * @return
     */
    List<Activity0505> findAllByEmail0505(String email);
}
