package com.publicinterface.constraint.constraint.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 数据库实体抽象 Model
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Data
public abstract class BaseModel implements ModelConstraint {

    public static final String CREATE_TIME_DB_FIELD = "create_time";

    public static final String UPDATE_TIME_DB_FIELD = "update_time";

    public static final String DEL_FLAG_DB_FIELD = "del_flag";

    @TableId(value = "id", type = IdType.UUID)
    protected String id;

    protected Date createTime;

    protected Date updateTime;

    protected Boolean delFlag;

}
