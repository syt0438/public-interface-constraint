package com.env.common.constraint.mvc;

import com.env.common.constraint.domain.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller 缓存层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Slf4j
public abstract class ControllerConstraintRedisImpl<T extends BaseModel>
        extends ControllerConstraintImpl<T>
        implements CacheConstraint {

    @Override
    @Cacheable(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#id")
    public T getById(@PathVariable("id") String id) {
        log.info("{}.{}查询DB: [{}]", this.getClass().getSimpleName(), "getById", id);

        return super.getById(id);
    }

    @Override
    @CachePut(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#bean.id")
    public T insert(T bean) {
        log.info("{}.{}查询DB: [{}]", this.getClass().getSimpleName(), "insert", bean);

        return super.insert(bean);
    }

    @Override
    @CachePut(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#bean.id")
    public T update(T bean) {
        return super.update(bean);
    }

    @Override
    @CacheEvict(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#id")
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    @Cacheable(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#result.id")
    public T search(T conditional) {
        return super.search(conditional);
    }

    @Override
    @Cacheable(value = "#root.target.actualCacheType()", key = "#root.target.cachePrefix()+#result.id")
    public T search(SearchConditional conditional) {
        return super.search(conditional);
    }

}
