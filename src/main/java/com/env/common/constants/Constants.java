package com.env.common.constants;

import com.env.common.constraint.EnumConstraint;
import lombok.Getter;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public abstract class Constants {

    public static final class ResponseKey {

        /**
         * 响应状态码 Key
         */
        public static final String CODE_KEY = "code";

        /**
         * 响应数据 Key
         */
        public static final String DATA_KEY = "data";

        /**
         * 响应消息 Key
         */
        public static final String MESSAGE_KEY = "message";

    }

    public enum ResponseStatus implements EnumConstraint {
        SUCCESS(200, "success"),
        ERROR(500, "error");

        @Getter
        private final String message;
        @Getter
        private final Integer statusCode;

        ResponseStatus(Integer statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        @Override
        public Integer enumItemOrdinal() {
            return getStatusCode();
        }

        @Override
        public String enumItemLabel() {
            return getMessage();
        }

    }

    public static final String AUTHENTICATION_KEY = "Authentication";
    public static final String USER_ID_KEY = "X-userId";
    public static final String USER_DETAILS_KEY = "X-userDetails";
    public static final String X_EXCEPTION_MESSAGE_KEY = "X-Exception-Message";

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;


}
