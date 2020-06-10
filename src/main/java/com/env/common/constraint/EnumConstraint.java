package com.env.common.constraint;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 统一枚举抽象层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface EnumConstraint extends Serializable {

    String ORDINAL_METHOD_NAME = "ordinal";
    String NAME_METHOD_NAME = "name";
    String VALUES_METHOD_NAME = "values";

    //region 基础类方法

    /**
     * 枚举类的类型名称，默认值为 Class.getSimpleName()
     */
    default String enumTypeName() {
        String value = this.getClass().getSimpleName();

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        if (StringUtils.contains(value, "Enum")) {
            value = StringUtils.remove(value, "Enum");
        }

        return value;
    }

    /**
     * 枚举类的标注（描述性信息），默认值为 null
     */
    default String enumTypeLabel() {
        return null;
    }

    /**
     * 枚举项的数字描述，默认值 Enum.ordinal()
     */
    default Integer enumItemOrdinal() {
        Method ordinalMethod = ReflectionUtils.findMethod(this.getClass(), ORDINAL_METHOD_NAME);

        if (Objects.isNull(ordinalMethod)) {
            return null;
        }

        Integer value = (Integer) ReflectionUtils.invokeMethod(ordinalMethod, this);

        if (Objects.isNull(value)) {
            return null;
        }

        return value;
    }

    /**
     * 枚举项的名称，默认值为  Enum.name()
     */
    default String enumItemName() {
        Method nameMethod = ReflectionUtils.findMethod(this.getClass(), NAME_METHOD_NAME);

        if (Objects.isNull(nameMethod)) {
            return null;
        }

        Object value = ReflectionUtils.invokeMethod(nameMethod, this);

        return (String) value;
    }

    /**
     * 枚举项的标注（描述性信息），默认值为 null
     */
    default String enumItemLabel() {
        return null;
    }

    /**
     * 枚举项的详细描述信息，默认值为 null
     */
    default String enumItemDescription() {
        return null;
    }

    //endregion

    //region 功能类方法

    /**
     * 根据 enumItemOrdinal 的值，查找当前枚举对应的枚举实例
     *
     * @param enumClass
     * @param enumItemOrdinal
     * @return
     */
    @SuppressWarnings("all")
    static <T extends EnumConstraint> Optional<T> findByEnumItemOrdinal(Class<T> enumClass, Integer enumItemOrdinal) {
        if (Objects.isNull(enumClass) || Objects.isNull(enumItemOrdinal)) {
            throw new IllegalArgumentException();
        }

        Method valuesMethod = ReflectionUtils.findMethod(enumClass, VALUES_METHOD_NAME);

        if (Objects.isNull(valuesMethod)) {
            throw new IllegalCallerException("调用异常，请检查调用类，是否为枚举");
        }

        T[] values = (T[]) ReflectionUtils.invokeMethod(valuesMethod, enumClass);

        if (ArrayUtils.isEmpty(values)) {
            return Optional.empty();
        }

        return Arrays.stream(values)
                .filter(value -> Objects.equals(value.enumItemOrdinal(), enumItemOrdinal))
                .findFirst();
    }


    /**
     * 根据 enumItemName 的值，查找当前枚举对应的枚举实例
     *
     * @param enumClass
     * @param enumItemName
     * @return
     */
    @SuppressWarnings("all")
    static <T extends EnumConstraint> Optional<T> findByEnumItemName(Class<T> enumClass, String enumItemName) {
        if (Objects.isNull(enumClass) || StringUtils.isEmpty(enumItemName)) {
            throw new IllegalArgumentException();
        }

        Method valuesMethod = ReflectionUtils.findMethod(enumClass, VALUES_METHOD_NAME);

        if (Objects.isNull(valuesMethod)) {
            throw new IllegalCallerException("调用异常，请检查调用类，是否为枚举");
        }

        T[] values = (T[]) ReflectionUtils.invokeMethod(valuesMethod, enumClass);

        if (ArrayUtils.isEmpty(values)) {
            return Optional.empty();
        }

        return Arrays.stream(values)
                .filter(value -> StringUtils.equals(value.enumItemName(), enumItemName))
                .findFirst();
    }


    /**
     * 根据 enumItemLabel 的值，查找当前枚举对应的枚举实例
     *
     * @param enumClass
     * @param enumItemLabel
     * @return
     */
    @SuppressWarnings("all")
    static <T extends EnumConstraint> Optional<T> findByEnumItemLabel(Class<T> enumClass, String enumItemLabel) {
        if (Objects.isNull(enumClass) || StringUtils.isEmpty(enumItemLabel)) {
            throw new IllegalArgumentException();
        }

        Method valuesMethod = ReflectionUtils.findMethod(enumClass, VALUES_METHOD_NAME);

        if (Objects.isNull(valuesMethod)) {
            throw new IllegalCallerException("调用异常，请检查调用类，是否为枚举");
        }

        T[] values = (T[]) ReflectionUtils.invokeMethod(valuesMethod, enumClass);

        if (ArrayUtils.isEmpty(values)) {
            return Optional.empty();
        }

        return Arrays.stream(values)
                .filter(value -> StringUtils.equals(value.enumItemLabel(), enumItemLabel))
                .findFirst();
    }

    //endregion
}
