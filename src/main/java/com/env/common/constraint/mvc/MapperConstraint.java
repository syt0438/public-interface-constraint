package com.env.common.constraint.mvc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.env.common.constraint.domain.BaseModel;

/**
 * Mapper抽象层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface MapperConstraint<T extends BaseModel> extends BaseMapper<T> {
}
