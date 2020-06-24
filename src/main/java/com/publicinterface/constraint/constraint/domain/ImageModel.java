package com.publicinterface.constraint.constraint.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片数组的抽象 Model
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ImageModel extends BaseModel {

    protected String images;

}
