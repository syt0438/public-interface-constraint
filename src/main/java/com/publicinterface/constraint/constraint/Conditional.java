package com.publicinterface.constraint.constraint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询的抽象实体
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@ApiModel("查询的抽象实体")
public class Conditional implements ConditionalConstraint {

    /**
     * 统计的开始时间
     */
    @ApiModelProperty("查询开始时间")
    protected Date startTime;

    /**
     * 统计的结束时间
     */
    @ApiModelProperty("查询结束时间")
    protected Date endTime;
}
