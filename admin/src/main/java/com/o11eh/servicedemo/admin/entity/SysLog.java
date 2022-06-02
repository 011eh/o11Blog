package com.o11eh.servicedemo.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.servicedemo.servicebase.enums.LogStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SysLog extends DataAccess {

    private static final long serialVersionUID = 1L;

    private String username;
    private String ip;
    private String operation;
    private String controller;
    private String method;
    private String uri;
    private String httpMethod;
    private Object params;
    private LogStatus logStatus;
    private int timeCost;
    private String exceptionMessage;

    public SysLog(String username,
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
        this.username = username;
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
