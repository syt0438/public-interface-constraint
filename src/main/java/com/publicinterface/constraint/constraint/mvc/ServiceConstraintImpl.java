package com.publicinterface.constraint.constraint.mvc;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.publicinterface.constraint.constraint.domain.BaseModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Service抽象实现类层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Slf4j
public abstract class ServiceConstraintImpl<M extends MapperConstraint<T>, T extends BaseModel>
        extends ServiceImpl<M, T>
        implements ServiceConstraint<T> {

    @Override
    public boolean save(T entity) {
        Date currentTime = new Date();

        entity.setCreateTime(currentTime);
        entity.setUpdateTime(currentTime);

        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 30);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        Date currentTime = new Date();

        for (T entity : entityList) {
            entity.setCreateTime(currentTime);
            entity.setUpdateTime(currentTime);
        }

        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        Date currentTime = new Date();

        if (Objects.isNull(entity.getId())) {
            entity.setCreateTime(currentTime);
        }

        entity.setUpdateTime(currentTime);

        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, 30);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Date currentTime = new Date();

        for (T entity : entityList) {
            if (Objects.isNull(entity.getId())) {
                entity.setCreateTime(currentTime);
            }

            entity.setUpdateTime(currentTime);
        }

        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public boolean updateById(T entity) {
        Date currentTime = new Date();

        entity.setUpdateTime(currentTime);

        return super.updateById(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        Date currentTime = new Date();

        entity.setUpdateTime(currentTime);

        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        Date currentTime = new Date();

        for (T entity : entityList) {
            entity.setUpdateTime(currentTime);
        }

        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public List<T> list() {
        return super.list(null);
    }

    @Override
    public int count() {
        return super.count(null);
    }

    @Override
    public boolean exists(String id) {
        T entity = super.getById(id);

        return Objects.nonNull(entity);
    }

}
