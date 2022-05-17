package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o11eh.servicedemo.admin.config.validation.StringId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @StringId
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
