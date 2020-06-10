package com.env.common.utils.function;

/**
 * 表示接受三个输入参数，并返回一个任何结果的操作。
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@FunctionalInterface
public interface TernaryFunction<T, U, I, R> {
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param i the third function argument
     * @return the function result
     */
    R apply(T t, U u, I i);
}
