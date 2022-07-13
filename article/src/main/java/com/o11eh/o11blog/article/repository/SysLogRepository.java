package com.o11eh.o11blog.article.repository;

import com.o11eh.o11blog.servicebase.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLog, String> {
}
