package com.scsa.user.dao;

import com.scsa.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:37
 */
public interface UserInfoDao extends JpaRepository<UserInfo, String> {

    UserInfo findByOpenid(String openid);
}
