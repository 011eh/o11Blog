package com.o11eh.servicedemo.front.repository;

import com.o11eh.servicedemo.front.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLog, String> {
}
