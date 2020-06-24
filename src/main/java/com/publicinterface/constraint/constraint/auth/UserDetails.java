package com.publicinterface.constraint.constraint.auth;

import lombok.Data;

/**
 * 用户信息的抽象接口基本实现
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
public class UserDetails implements UserDetailsConstraint {

    private String userId;

    private String identityType;

    private String identifier;

    private String token;

    private Long loginTime;

    private Long expireTime;

}
