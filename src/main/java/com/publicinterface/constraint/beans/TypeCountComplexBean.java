package com.publicinterface.constraint.beans;

import com.publicinterface.constraint.constraint.QueryResultConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypeCountComplexBean<T> extends TypeCountBean<T> implements QueryResultConstraint {
    private List<T> subDataList;

    public TypeCountComplexBean(List<T> subDataList) {
        this.subDataList = subDataList;
    }

    public TypeCountComplexBean(Long totalCount, List<T> subList) {
        super(totalCount);

        this.subDataList = subList;
    }
}
