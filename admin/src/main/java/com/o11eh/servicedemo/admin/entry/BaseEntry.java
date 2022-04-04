package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o11eh.servicedemo.admin.utils.validate.Create;
import com.o11eh.servicedemo.admin.utils.validate.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntry<T extends Model<T>> extends Model<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
