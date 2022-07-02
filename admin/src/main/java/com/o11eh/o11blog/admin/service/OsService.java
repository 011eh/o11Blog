package com.o11eh.o11blog.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface OsService {
    String uploadFile(MultipartFile file);
}
