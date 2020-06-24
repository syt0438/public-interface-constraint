package com.publicinterface.constraint.beans.core;

import java.io.Serializable;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface IResponseBean<T> extends Serializable {

    IResponseBean<T> success();

    IResponseBean<T> success(T data);

    IResponseBean<T> success(T data, String message);

    IResponseBean<T> error();

    IResponseBean<T> error(int statusCode);

    IResponseBean<T> error(String message);

    IResponseBean<T> error(int statusCode, String message);

    IResponseBean<T> setData(T data);

    T getData();

    int getCode();

    String getMessage();
}
