package com.yuki.usercenter.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yuki.usercenter.common.AliyunSecret;
import jodd.io.FileNameUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class UpdateImagUtils {
    public static final String ALI_DOMAIN = "https://yuumoko.oss-cn-chengdu.aliyuncs.com/";

    public static String uploadImage(MultipartFile file) throws IOException {
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String ext = "." + FileNameUtil.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + "." + ext;
        // 地域节点
        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = AliyunSecret.ACCESS_KEY_ID.getSecret();
        String accessKeySecret = AliyunSecret.ACCESS_KEY_SECRET.getSecret();
        // OSS客户端服务对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(
                "yuumoko", // 仓库名
                fileName, // 文件名
                file.getInputStream()
        );
        return ALI_DOMAIN + fileName;
    }
}
