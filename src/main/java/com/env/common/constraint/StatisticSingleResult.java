package com.env.common.constraint;

import com.env.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Objects;

/**
 * 统计的单结果返回实体
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("统计的单结果返回实体")
public class StatisticSingleResult<T> extends StatisticResult implements QueryResultConstraint {

    private T data;

    public StatisticSingleResult() {
    }

    public StatisticSingleResult(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StatisticSingleResult(T data, Date startTime, Date endTime) {
        this.data = data;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StatisticSingleResult(DateUtil.StartToEndTimeBean timeBean) {
        Objects.requireNonNull(timeBean, "timeBean");

        this.startTime = timeBean.getStartTime();
        this.endTime = timeBean.getEndTime();
    }
}
