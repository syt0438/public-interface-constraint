package com.env.common.constraint.mvc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.env.common.constraint.domain.BaseModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

import static com.env.common.constants.Constants.DEFAULT_PAGE_INDEX;
import static com.env.common.constants.Constants.DEFAULT_PAGE_SIZE;
import static com.env.common.constraint.domain.BaseModel.CREATE_TIME_DB_FIELD;

/**
 * Controller 公共接口实现层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Slf4j
public abstract class ControllerConstraintImpl<T extends BaseModel> implements ControllerConstraint<T>, ApplicationContextAware {

    protected ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    protected abstract ServiceConstraint<T> getService();

    @Override
    public T getById(@PathVariable("id") String id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id 不能为空");
        }

        return getService().getById(id);
    }

    @Override
    public List<T> list(@PathVariable("pageSize") Integer pageSize) {
        PageHelper.startPage(DEFAULT_PAGE_INDEX, Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        return getService().list(queryWrapper);
    }

    @Override
    public PageInfo<T> list(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<T> page = PageHelper.startPage(Objects.requireNonNullElse(pageNum, DEFAULT_PAGE_INDEX), Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        List<T> data = getService().list(queryWrapper);

        PageInfo<T> result = page.toPageInfo();
        result.setList(data);

        return result;
    }

    @Override
    public List<T> search(@RequestBody(required = false) SearchConditional conditional, @PathVariable("pageSize") Integer pageSize) {
        PageHelper.startPage(DEFAULT_PAGE_INDEX, Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));

        QueryWrapper<T> queryWrapper = buildMutexQueryWrapper(conditional);

        return getService().list(queryWrapper);
    }

    @Override
    public List<T> search(@RequestBody(required = false) T conditional, @PathVariable("pageSize") Integer pageSize) {
        PageHelper.startPage(DEFAULT_PAGE_INDEX, Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(conditional);

        List<T> list = getService().list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list;
    }

    @Override
    public T search(@RequestBody(required = false) SearchConditional conditional) {
        PageHelper.startPage(DEFAULT_PAGE_INDEX, 1);

        QueryWrapper<T> queryWrapper = buildMutexQueryWrapper(conditional);

        List<T> list = getService().list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public T search(@RequestBody(required = false) T conditional) {
        List<T> list = search(conditional, 1);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public PageInfo<T> search(@RequestBody(required = false) SearchConditional conditional, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<T> page = PageHelper.startPage(Objects.requireNonNullElse(pageNum, DEFAULT_PAGE_INDEX), Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));

        QueryWrapper<T> queryWrapper = buildMutexQueryWrapper(conditional);

        getService().list(queryWrapper);

        return new PageInfo<>(page);
    }

    @Override
    public T insert(@Validated @RequestBody T bean) {
        bean.setId(null);

        if (!getService().save(bean)) {
            throw new IllegalStateException("保存失败");
        }

        return bean;
    }

    @Override
    public T update(@Validated @RequestBody T bean) {
        if (!getService().updateById(bean)) {
            throw new IllegalStateException("更新失败");
        }

        return bean;
    }

    @Override
    public void deleteById(@PathVariable("id") String id) {
        getService().removeById(id);
    }

    @Override
    public void deleteById(@RequestBody List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        ids.forEach(this::deleteById);
    }

    @Override
    public int count() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        return getService().count(queryWrapper);
    }

    private QueryWrapper<T> buildMutexQueryWrapper(@RequestBody(required = false) SearchConditional conditional) {
        Class<T> genericActualClass = getGenericActualClass(this.getClass());

        QueryWrapper<T> queryWrapper = QueryWrapperProxyFactory.proxy(new QueryWrapper<>(), genericActualClass);

        conditional.getSearchItems()
                .stream()
                .filter(item -> Objects.nonNull(item.getSearchType()))
                .forEach(item -> item.invokeSearchTypeM(queryWrapper));

        conditional.getSearchItems()
                .stream()
                .filter(item -> Objects.nonNull(item.getOrderType()))
                .forEach(item -> item.invokeOrderTypeM(queryWrapper));

        conditional.getSearchItems()
                .stream()
                .filter(item -> Objects.nonNull(item.getConsumer()))
                .forEach(item -> item.invokeConsumeM(queryWrapper));


        if (Objects.nonNull(conditional.getStartTime())) {
            queryWrapper.ge(CREATE_TIME_DB_FIELD, conditional.getStartTime());
        }

        if (Objects.nonNull(conditional.getEndTime())) {
            queryWrapper.lt(CREATE_TIME_DB_FIELD, conditional.getEndTime());
        }
        return queryWrapper;
    }

}
