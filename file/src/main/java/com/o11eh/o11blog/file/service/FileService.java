package com.o11eh.o11blog.file.service;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.o11eh.o11blog.file.config.AliyunProperties;
import com.o11eh.o11blog.servicebase.config.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {

    private AliyunProperties properties;

    public String uploadFile(MultipartFile file, String dir) {
        String endpoint = properties.getEndpoint();
        String bucketName = properties.getBucketName();
        String accessKeyId = properties.getAccessKeyId();
        String accessKeySecret = properties.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        try {
            String objectName = dir + IdUtil.fastSimpleUUID() + StrUtil.DOT + FileNameUtil.extName(file.getOriginalFilename());
            ossClient.putObject(bucketName, objectName, file.getInputStream());
            log.info("上传成功");
            return StrUtil.format("https://{}.{}/{}", bucketName, endpoint, objectName);
        } catch (OSSException e) {
            log.error("上传错误");
            log.error("错误信息：" + e.getErrorMessage());
            log.error("错误码：" + e.getErrorCode());
            log.error("请求ID：" + e.getRequestId());
            log.error("主机ID：" + e.getHostId());
            throw BusinessException.e("上传错误");
        } catch (ClientException | IOException e) {
            log.error("客户端错误" + e.getMessage());
            throw BusinessException.e("上传错误");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
