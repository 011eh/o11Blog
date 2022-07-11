package com.o11eh.o11blog.file.controller;

import com.o11eh.o11blog.file.service.FileService;
import com.o11eh.o11blog.servicebase.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件服务")
@RestController
@AllArgsConstructor
public class FileController {

    private static final String ADMIN_AVATAR_DIR = "admin/avatar/";
    private static final String BLOG_PICTURE_DIR = "blog/";

    private FileService fileService;

    @ApiOperation("上传管理员头像")
    @PostMapping("/adminAvatarUpload")
    public Result uploadAvatar(MultipartFile file) {
        String url = fileService.uploadFile(file, ADMIN_AVATAR_DIR);
        return Result.success(url);
    }

    @ApiOperation("上传博客封面")
    @PostMapping("/blog")
    public Result uploadBlog(MultipartFile file) {
        String url = fileService.uploadFile(file, BLOG_PICTURE_DIR);
        return Result.success(url);
    }
}
