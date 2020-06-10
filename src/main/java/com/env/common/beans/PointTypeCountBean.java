package com.env.common.beans;

import com.env.common.constraint.QueryResultConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PointTypeCountBean<T> extends TypeCountBean<T> implements QueryResultConstraint {

    private Double longitude;

    private Double latitude;

}
