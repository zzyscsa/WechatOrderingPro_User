package com.scsa.user.service.impl;

import com.scsa.user.dao.UserInfoDao;
import com.scsa.user.dataobject.UserInfo;
import com.scsa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoDao.findByOpenid(openid);
    }
}
