package com.yuqi.mrrs.thirdservice.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.net.URL;
import java.util.Date;

@Component
public class OssUtil {

    @Value("${oss.endpoint}")
    String endpoint;

    @Value("${oss.accessKeyId}")
    String accessKeyId;

    @Value("${oss.accessKeySecret}")
    String accessKeySecret;

    @Value("${oss.bucketName}")
    String bucketName;

    public  String uploadFile(File file,String type){

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//// 上传文件流。
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream("<yourlocalFile>");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        URL url = null;
        if(type.equals("subject")) {
            ossClient.putObject(bucketName, "subject/" + file.getName(), file);
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL，第一个参数为bucketName，第二个参数key为上传的文件路径名称，第三个为过期时间
            url = ossClient.generatePresignedUrl(bucketName, "subject/" + file.getName(), expiration);
        }
        else if(type.equals("celebrity")){
            ossClient.putObject(bucketName, "celebrity/" + file.getName(), file);
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL，第一个参数为bucketName，第二个参数key为上传的文件路径名称，第三个为过期时间
            url = ossClient.generatePresignedUrl(bucketName, "celebrity/" + file.getName(), expiration);
        }
// 关闭OSSClient。
        ossClient.shutdown();

        return url.toString();
    }


}
