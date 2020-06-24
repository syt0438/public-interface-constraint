package com.publicinterface.constraint.utils.function;

/**
 * 表示接受四个输入参数，并没有任何返回结果的操作。
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@FunctionalInterface
public interface QuaternaryConsumer<T, U, I, F> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param i the third function argument
     * @param f the third function argument
     */
    void accept(T t, U u, I i, F f);

}
