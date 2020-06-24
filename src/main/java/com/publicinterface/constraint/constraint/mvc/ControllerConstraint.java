package com.publicinterface.constraint.constraint.mvc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.publicinterface.constraint.constraint.Conditional;
import com.publicinterface.constraint.constraint.EnumConstraint;
import com.publicinterface.constraint.constraint.domain.BaseModel;
import com.publicinterface.constraint.constraint.domain.ModelConstraint;
import com.publicinterface.constraint.utils.function.TernaryConsumer;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.publicinterface.constraint.constraint.mvc.ControllerConstraint.OrderType.DESC;
import static com.publicinterface.constraint.constraint.mvc.ControllerConstraint.SearchType.EQUALS;
import static java.util.Collections.EMPTY_LIST;

/**
 * Controller 公共接口层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface ControllerConstraint<T extends BaseModel> {

    @ApiOperation(value = "公共接口，根据id查询")
    @GetMapping("/{id}")
    T getById(@PathVariable("id") String id);


    @ApiOperation(value = "公共接口，分页查询")
    @GetMapping(value = "/list/{pageSize}")
    List<T> list(@PathVariable("pageSize") Integer pageSize);

    @ApiOperation(value = "公共接口，分页查询")
    @GetMapping(value = "/{pageNum}/{pageSize}")
    PageInfo<T> list(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize);

    @ApiOperation(value = "公共接口，搜索，支持 多字段查询、多字段排序、按时间搜索")
    @PostMapping(value = "/search/list/{pageSize}")
    List<T> search(@RequestBody(required = false) SearchConditional conditional, @PathVariable("pageSize") Integer pageSize);

    @ApiOperation(value = "公共接口，搜索，支持多字段查询")
    @PostMapping(value = "/search/list/simple/{pageSize}")
    List<T> search(@RequestBody(required = false) T conditional, @PathVariable("pageSize") Integer pageSize);

    @ApiOperation(value = "公共接口，搜索，支持 多字段查询、多字段排序、按时间搜索")
    @PostMapping(value = "/search/one")
    T search(@RequestBody(required = false) SearchConditional conditional);

    @ApiOperation(value = "公共接口，搜索，支持多字段查询")
    @PostMapping(value = "/search/one/simple")
    T search(@RequestBody(required = false) T conditional);

    @ApiOperation(value = "公共接口，搜索，支持 多字段查询、多字段排序、按时间搜索、分页")
    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    PageInfo<T> search(@RequestBody(required = false) SearchConditional conditional, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize);

    @ApiOperation(value = "公共接口，创建")
    @PostMapping
    T insert(@Validated @RequestBody T bean);

    @ApiOperation(value = "公共接口，更新")
    @PutMapping
    T update(@Validated @RequestBody T bean);

    @ApiOperation(value = "公共接口，单个删除")
    @DeleteMapping(value = "/{id}")
    void deleteById(@PathVariable("id") String id);

    @ApiOperation(value = "公共接口，批量删除")
    @DeleteMapping
    void deleteById(@RequestBody List<String> id);

    @ApiOperation(value = "公共接口，获取记录数")
    @GetMapping("/count")
    int count();

    @ApiModel("查询类型")
    enum SearchType implements EnumConstraint {
        @ApiModelProperty("精确查询")
        EQUALS("精确查询", (queryWrapper, key, value) -> {
            if (StringUtils.isBlank(key) || Objects.isNull(value)) {
                return;
            }

            queryWrapper.eq(key, value);
        }),

        @ApiModelProperty("模糊查询")
        LIKE("模糊查询", (queryWrapper, key, value) -> {
            if (StringUtils.isBlank(key) || Objects.isNull(value)) {
                return;
            }

            queryWrapper.like(key, value);
        }),

        @ApiModelProperty("左边模糊查询")
        LIKE_LEFT("左边模糊查询", (queryWrapper, key, value) -> {
            if (StringUtils.isBlank(key) || Objects.isNull(value)) {
                return;
            }

            queryWrapper.likeLeft(key, value);
        }),

        @ApiModelProperty("右边模糊查询")
        LIKE_RIGHT("右边模糊查询", (queryWrapper, key, value) -> {
            if (StringUtils.isBlank(key) || Objects.isNull(value)) {
                return;
            }

            queryWrapper.likeRight(key, value);
        }),
        ;

        private final String itemLabel;

        private final TernaryConsumer<QueryWrapper<? extends ModelConstraint>, String, Object> m;

        SearchType(String itemLabel, TernaryConsumer<QueryWrapper<? extends ModelConstraint>, String, Object> m) {
            this.itemLabel = itemLabel;
            this.m = m;
        }

        @Override
        public String enumItemLabel() {
            return this.itemLabel;
        }
    }

    @ApiModel("排序类型")
    enum OrderType implements EnumConstraint {
        @ApiModelProperty("正序")
        ASC("正序", ((queryWrapper, key) -> {
            if (StringUtils.isBlank(key)) {
                return;
            }

            queryWrapper.orderByAsc(key);
        })),

        @ApiModelProperty("倒序")
        DESC("倒序", ((queryWrapper, key) -> {
            if (StringUtils.isBlank(key)) {
                return;
            }

            queryWrapper.orderByDesc(key);
        })),
        ;

        private final String itemLabel;

        private final BiConsumer<QueryWrapper<?>, String> m;

        OrderType(String itemLabel, BiConsumer<QueryWrapper<?>, String> method) {
            this.itemLabel = itemLabel;
            this.m = method;
        }

        @Override
        public String enumItemLabel() {
            return this.itemLabel;
        }
    }

    @Data
    @ApiModel("搜索项")
    class SearchItem implements Serializable {
        @ApiModelProperty("字段名称")
        private String key;

        @ApiModelProperty("需要查询的字段值")
        private Object value;

        @ApiModelProperty("非 null 则将该字段纳入排序字段")
        private OrderType orderType;

        @ApiModelProperty("查询类型")
        private SearchType searchType;

        private transient Consumer<QueryWrapper<? extends ModelConstraint>> consumer;

        public SearchItem() {
        }

        public SearchItem(Consumer<QueryWrapper<? extends ModelConstraint>> consumer) {
            this.consumer = consumer;
        }

        public SearchItem(String key, Object value) {
            this(key, value, EQUALS);
        }

        public SearchItem(String key, OrderType orderType) {
            this(key, null, orderType);
        }

        public SearchItem(String key, Object value, SearchType searchType) {
            this.key = key;
            this.value = value;
            this.searchType = Objects.requireNonNullElse(searchType, EQUALS);
        }

        public SearchItem(String key, Object value, OrderType orderType) {
            this.key = key;
            this.value = value;
            this.orderType = Objects.requireNonNullElse(orderType, DESC);
        }

        public SearchItem(String key, Object value, SearchType searchType, OrderType orderType) {
            this.key = key;
            this.value = value;
            this.searchType = Objects.requireNonNullElse(searchType, EQUALS);
            this.orderType = Objects.requireNonNullElse(orderType, DESC);
        }

        public <T extends ModelConstraint> void invokeSearchTypeM(QueryWrapper<T> queryWrapper) {
            Optional.ofNullable(searchType)
                    .ifPresent(s -> s.m.accept(queryWrapper, key, value));
        }

        public <T extends ModelConstraint> void invokeOrderTypeM(QueryWrapper<T> queryWrapper) {
            Optional.ofNullable(orderType)
                    .ifPresent(o -> o.m.accept(queryWrapper, key));
        }

        public <T extends ModelConstraint> void invokeConsumeM(QueryWrapper<T> queryWrapper) {
            Optional.ofNullable(consumer)
                    .ifPresent(c -> c.accept(queryWrapper));
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ApiModel("查询实体")
    class SearchConditional extends Conditional {
        private List<ControllerConstraint.SearchItem> searchItems = Collections.emptyList();

        public void condition(Consumer<QueryWrapper<? extends ModelConstraint>> consumer) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(consumer));
        }

        public void condition(@NotNull String key, @NotNull Object value) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(key, value));
        }

        public void condition(@NotNull String key, @NotNull OrderType orderType) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(key, orderType));
        }

        public void condition(@NotNull String key, @NotNull String value, SearchType searchType) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(key, value, searchType));
        }

        public void condition(@NotNull String key, @NotNull String value, OrderType orderType) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(key, value, orderType));
        }

        public void condition(@NotNull String key, @NotNull String value, SearchType searchType, OrderType orderType) {
            if (searchItems == EMPTY_LIST) {
                searchItems = new LinkedList<>();
            }

            searchItems.add(new SearchItem(key, value, searchType, orderType));
        }
    }

    @SuppressWarnings("all")
    default Class<T> getGenericActualClass(Class clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            return (Class<T>) actualTypeArguments[0];
        }

        throw new IllegalStateException();
    }
}
