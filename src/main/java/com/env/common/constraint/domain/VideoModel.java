package com.env.common.constraint.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视频数组的抽象 Model
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class VideoModel extends BaseModel {

    protected String videos;

}
