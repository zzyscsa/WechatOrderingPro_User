package com.scsa.user.controller;

import com.scsa.user.VO.ResultVO;
import com.scsa.user.constant.CookieConstant;
import com.scsa.user.constant.RedisConstant;
import com.scsa.user.dataobject.UserInfo;
import com.scsa.user.enums.ResultEnum;
import com.scsa.user.enums.RoleEnum;
import com.scsa.user.service.UserService;
import com.scsa.user.util.CookieUtil;
import com.scsa.user.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: SCSA
 * @Date: 2020/8/25 17:45
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {

        //1. openid和数据库匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //2. 判断角色（卖家?卖家）
        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //3. 设置cookie
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }

    /**
     * 卖家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletResponse response,
                           HttpServletRequest request) {

        //0. 判断是否已登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
            return ResultVOUtil.success();
        }

        //1. openid和数据库匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //2. 判断角色（卖家?卖家）
        if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //3. redis写入
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token),
                                        openid,
                                        expire,
                                        TimeUnit.SECONDS);

        //4. 设置cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }

}
