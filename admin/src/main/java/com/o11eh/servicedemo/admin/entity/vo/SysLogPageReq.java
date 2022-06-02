package com.o11eh.servicedemo.admin.entity.vo;

import com.o11eh.servicedemo.servicebase.entry.PageReq;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysLogPageReq extends PageReq {

    private List<String> operators;
    private String ip;
    private String operation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
