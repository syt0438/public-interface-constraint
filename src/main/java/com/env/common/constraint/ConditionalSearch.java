package com.env.common.constraint;

/**
 * @author Linshu 745698872@qq.com
 *
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础的查询的实体
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("基础的查询的实体")
public class ConditionalSearch extends Conditional implements ConditionalConstraint {
    /**
     * 分页参数：页索引
     */
    @ApiModelProperty("分页参数：页索引")
    private Integer pageNum = 1;

    /**
     * 分页参数：页大小
     */
    @ApiModelProperty("分页参数：页大小")
    private Integer pageSize = 10;
}
