package com.env.common.beans.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import static com.env.common.constants.Constants.ResponseStatus.ERROR;
import static com.env.common.constants.Constants.ResponseStatus.SUCCESS;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseBean<T> implements IResponseBean<T> {
    @Getter
    private int code;

    @Getter
    private String message;

    @Setter
    @Getter
    private T data;

    @Override
    public IResponseBean<T> success() {
        this.code = SUCCESS.getStatusCode();
        this.message = SUCCESS.getMessage();

        return this;
    }

    @Override
    public IResponseBean<T> success(T data) {
        this.code = SUCCESS.getStatusCode();
        this.message = SUCCESS.getMessage();
        setData(data);

        return this;
    }

    @Override
    public IResponseBean<T> success(T data, String message) {
        this.code = SUCCESS.getStatusCode();
        this.message = StringUtils.isNotBlank(message) ? message : SUCCESS.getMessage();
        setData(data);

        return this;
    }

    @Override
    public IResponseBean<T> error() {
        this.code = ERROR.getStatusCode();
        this.message = ERROR.getMessage();

        return this;
    }

    @Override
    public IResponseBean<T> error(int statusCode) {
        this.code = statusCode;
        this.message = ERROR.getMessage();

        return this;
    }

    @Override
    public IResponseBean<T> error(String message) {
        this.code = ERROR.getStatusCode();
        this.message = StringUtils.isNoneBlank(message) ? message : ERROR.getMessage();

        return this;
    }

    @Override
    public IResponseBean<T> error(int statusCode, String message) {
        this.code = statusCode;
        this.message = StringUtils.isNoneBlank(message) ? message : ERROR.getMessage();

        return this;
    }
}
