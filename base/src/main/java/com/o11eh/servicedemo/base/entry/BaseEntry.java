package com.o11eh.servicedemo.base.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.o11eh.servicedemo.base.validation.groups.Add;
import com.o11eh.servicedemo.base.validation.groups.Query;
import com.o11eh.servicedemo.base.validation.groups.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseEntry<T extends Model<T>> extends Model<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = {Query.class, Update.class})
    @Null(groups = Add.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
