package com.env.common.constraint.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片与视频的抽象 Model
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseResourceModel extends BaseModel {

    protected String images;

    protected String videos;

}
