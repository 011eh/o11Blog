package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.o11eh.servicedemo.admin.enums.LogStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String userId;
    private String ip;
    private String operation;
    private String controller;
    private String url;
    private String method;
    private Object params;
    private LogStatus logStatus;
    private int timeCost;
    private String exceptionMessage;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    public SysLog(String userId,
                  String ip,
                  String operation,
                  String controller,
                  String url,
                  String method,
                  Object params,
                  LogStatus logStatus,
                  int timeCost) {
        this.userId = userId;
        this.ip = ip;
        this.operation = operation;
        this.controller = controller;
        this.url = url;
        this.method = method;
        this.params = params;
        this.logStatus = logStatus;
        this.timeCost = timeCost;
        this.exceptionMessage = exceptionMessage;
    }
}
