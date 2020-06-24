package com.publicinterface.constraint.constraint.auth;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface LoginConstraint {

    UserDetailsConstraint login(LoginParamConstraint loginParam);

}
