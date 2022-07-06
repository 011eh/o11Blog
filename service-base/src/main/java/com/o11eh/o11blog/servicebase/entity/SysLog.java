package com.o11eh.o11blog.servicebase.entity;

import com.o11eh.o11blog.servicebase.enums.LogStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "sys_log_front")
@DynamicInsert
@NoArgsConstructor
public class SysLog {

    @Id
    @Length(max = 19)
    @GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.o11eh.o11blog.front.config.SnowFlakeIdGenerator")
    private String id;

    @CreatedBy
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;

    public SysLog(String ip,
                  String operation,
                  String controller,
                  String method, String uri,
                  String httpMethod,
                  String params,
                  LogStatus logStatus,
                  int timeCost,
                  String exceptionMessage) {
        this.ip = ip;
        this.operation = operation;
        this.controller = controller;
        this.method = method;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.params = params;
        this.logStatus = logStatus;
        this.timeCost = timeCost;
        this.exceptionMessage = exceptionMessage;
    }

    public SysLog(String username, String ip, String operation, String controller, String method, String uri, String httpMethod, String params, LogStatus logStatus, int timeCost, String exceptionMessage) {
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
        this.exceptionMessage = exceptionMessage;
    }
}
