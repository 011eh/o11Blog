package com.o11eh.o11blog.front.repository;

import com.o11eh.o11blog.servicebase.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLog, String> {
}
