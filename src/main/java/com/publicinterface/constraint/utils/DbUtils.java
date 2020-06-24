package com.publicinterface.constraint.utils;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库column和实体property的映射工具
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public abstract class DbUtils {

    private static final Map<Class<?>, Map<String, String>> fieldMappingColumnInfo = new ConcurrentHashMap<>(2 << 5);

    public static String findColumnByField(Class<?> clazz, String field) {
        initMappingInfo(clazz);

        Optional<String> optional = Optional.ofNullable(fieldMappingColumnInfo.get(clazz).get(field));

        return optional.orElse(field);
    }

    private static void initMappingInfo(Class<?> clazz) {
        if (fieldMappingColumnInfo.containsKey(clazz)) {
            return;
        }

        synchronized (DbUtils.class) {
            if (fieldMappingColumnInfo.containsKey(clazz)) {
                return;
            }

            TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
            List<TableFieldInfo> fieldList = tableInfo.getFieldList();

            Map<String, String> fieldMappingColumn = new ConcurrentHashMap<>();

            fieldList.forEach(fieldInfo -> {
                fieldMappingColumn.put(fieldInfo.getProperty(), fieldInfo.getColumn());
            });

            fieldMappingColumnInfo.put(clazz, fieldMappingColumn);
        }
    }

}
