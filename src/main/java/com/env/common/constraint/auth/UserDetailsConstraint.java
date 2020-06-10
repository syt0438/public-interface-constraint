package com.env.common.constraint.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户信息的抽象接口
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@ApiModel(value = "用户信息")
public interface UserDetailsConstraint {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    String getUserId();

    @ApiModelProperty(value = "登录类型")
    String getIdentityType();


    /**
     * 登录标识（手机号 邮箱 用户名或第三方应用的 openID）
     */
    @ApiModelProperty(value = "登录标识（手机号 邮箱 用户名或第三方应用的 openID）")
    String getIdentifier();

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌")
    String getToken();

    @ApiModelProperty(value = "登录时间 ms")
    Long getLoginTime();

    @ApiModelProperty(value = "过期时间 ms")
    Long getExpireTime();

    default boolean tokenExpired() {
        long currentTime = System.currentTimeMillis();

        return currentTime > getExpireTime();
    }

}
