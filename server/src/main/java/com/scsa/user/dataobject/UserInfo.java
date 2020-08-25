package com.scsa.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:35
 */
@Entity
@Data
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;
}
