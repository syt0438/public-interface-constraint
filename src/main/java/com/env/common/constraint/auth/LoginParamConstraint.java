package com.env.common.constraint.auth;

import java.io.Serializable;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface LoginParamConstraint extends Serializable {

    /**
     * 授权码
     */
    String getCode();
}
