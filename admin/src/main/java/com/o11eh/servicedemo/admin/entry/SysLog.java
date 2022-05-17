package com.o11eh.servicedemo.admin.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.servicedemo.admin.enums.LogStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_log")
@NoArgsConstructor
public class SysLog extends DataAccess {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String ip;
    private String operation;
    private String controller;
    private String method;
    private String uri;
    private String httpMethod;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object params;
    private LogStatus logStatus;
    private int timeCost;
    private String exceptionMessage;

    @TableField(exist = false)
    private String username;

    public SysLog(String userId,
                  String ip,
                  String operation,
                  String controller,
                  String method,
                  String uri,
                  String httpMethod,
                  Object params,
                  LogStatus logStatus,
                  int timeCost,
                  String exception) {
        this.userId = userId;
        this.ip = ip;
        this.operation = operation;
        this.controller = controller;
        this.method = method;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.params = params;
        this.logStatus = logStatus;
        this.timeCost = timeCost;
        this.exceptionMessage = exception;
    }

}
