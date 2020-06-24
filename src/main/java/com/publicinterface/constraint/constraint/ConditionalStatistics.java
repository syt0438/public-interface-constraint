package com.publicinterface.constraint.constraint;

import com.publicinterface.constraint.utils.DateUtil;
import com.publicinterface.constraint.utils.TimeUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基础的统计查询的抽象实体
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("基础的统计查询的抽象实体")
public class ConditionalStatistics extends Conditional implements ConditionalConstraint {

    @ApiModelProperty("统计的时间周期 默认为 1 [秒/分钟/小时/天/月/年]")
    protected Integer timeDuration = 1;

    @ApiModelProperty("统计的时间周期 时间单位 默认为 MONTH")
    protected TimeUnit timeUnit = TimeUnit.MONTH;

    public List<DateUtil.StartToEndTimeBean> allStartToEndTime() {
        return allStartToEndTime(this.startTime, this.endTime, this.timeDuration);
    }

    public <R> List<R> allStartToEndTime(Function<DateUtil.StartToEndTimeBean, R> function) {
        Objects.requireNonNull(function, "function must be not null");

        return allStartToEndTime(this.startTime, this.endTime, this.timeDuration)
                .stream()
                .map(function)
                .collect(Collectors.toList());
    }

    public <R> List<StatisticManyResult<R>> allStartToEndTimeWithManyResultStatistics(BiFunction<Date, Date, List<R>> handler) {
        Objects.requireNonNull(handler, "handler must be not null");

        return allStartToEndTime(this.startTime, this.endTime, this.timeDuration)
                .stream()
                .map(StatisticManyResult<R>::new)
                .peek(dataItem -> dataItem.setData(handler.apply(dataItem.getStartTime(), dataItem.getEndTime())))
                .collect(Collectors.toList());
    }

    public <R> List<StatisticSingleResult<R>> allStartToEndTimeWithSingleResultStatistics(BiFunction<Date, Date, R> function) {
        Objects.requireNonNull(function, "function must be not null");

        return allStartToEndTime(this.startTime, this.endTime, this.timeDuration)
                .stream()
                .map(StatisticSingleResult<R>::new)
                .peek(dataItem -> dataItem.setData(function.apply(dataItem.getStartTime(), dataItem.getEndTime())))
                .collect(Collectors.toList());
    }

    private List<DateUtil.StartToEndTimeBean> allStartToEndTime(Date startTime, Date endTime, Integer duration) {
        Objects.requireNonNull(startTime, "startTime must be not null");
        Objects.requireNonNull(endTime, "endTime must be not null");
        Objects.requireNonNull(duration, "duration must be not null");

        return timeUnit.allStartToEndTime(startTime, endTime, duration);
    }
}
