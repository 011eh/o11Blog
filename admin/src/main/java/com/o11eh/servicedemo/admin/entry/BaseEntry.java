package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o11eh.servicedemo.admin.utils.validate.Create;
import com.o11eh.servicedemo.admin.utils.validate.Update;
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

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", insertStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
