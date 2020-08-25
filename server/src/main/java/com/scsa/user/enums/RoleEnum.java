package com.scsa.user.enums;

import lombok.Getter;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:57
 */
@Getter
public enum RoleEnum {

    BUYER(1, "买家"),
    SELLER(2, "卖家"),
    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
