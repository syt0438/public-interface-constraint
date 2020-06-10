package com.env.common.constraint;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 统计结果的抽象实体
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@ApiModel("统计结果的抽象实体")
public abstract class StatisticResult implements QueryResultConstraint {
    protected Date startTime;
    protected Date endTime;
}
