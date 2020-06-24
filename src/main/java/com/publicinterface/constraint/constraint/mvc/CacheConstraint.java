package com.publicinterface.constraint.constraint.mvc;

/**
 * 缓存层约束
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface CacheConstraint {

    String cachePrefix();

    String actualCacheType();

}
