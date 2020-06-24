package com.publicinterface.constraint.constraint.mvc;

import com.baomidou.mybatisplus.extension.service.IService;
import com.publicinterface.constraint.constraint.domain.BaseModel;

import java.util.List;

/**
 * Service抽象层
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public interface ServiceConstraint<T extends BaseModel> extends IService<T> {

    List<T> list();

    int count();

    boolean exists(String id);
}
