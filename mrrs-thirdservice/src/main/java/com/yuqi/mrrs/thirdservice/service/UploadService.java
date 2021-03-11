package com.yuqi.mrrs.thirdservice.service;

import com.yuqi.mrrs.thirdservice.oss.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    @Autowired
    OssUtil ossUtil;

    public String ossUpload(MultipartFile file,String imageType){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = null;
        if (imageType.equals("subject")) {
            filePath = "D:/Jiabin.huang/mrrs/images/subject/";
        }
        if (imageType.equals("celebrity")) {
            filePath = "D:/Jiabin.huang/mrrs/images/celebrity/";
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            String url = ossUtil.uploadFile(dest, imageType);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传失败！";

    }



    }


