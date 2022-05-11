package com.o11eh.servicedemo.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    void uploadFile(MultipartFile file);
}
