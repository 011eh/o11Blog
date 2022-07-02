package com.o11eh.o11blog.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o11eh.o11blog.servicebase.enums.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SysLog extends DataAccess {

    private static final long serialVersionUID = 1L;

    private String username;
    private String ip;
    private String operation;
    private String controller;
    private String method;
    private String uri;
    private String httpMethod;
    private String params;
    private LogStatus logStatus;
    private int timeCost;
    private String exceptionMessage;

}
