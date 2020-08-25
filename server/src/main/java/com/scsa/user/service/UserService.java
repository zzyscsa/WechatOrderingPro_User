package com.scsa.user.service;

import com.scsa.user.dataobject.UserInfo;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:38
 */
public interface UserService {

    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
