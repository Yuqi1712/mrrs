package com.yuqi.mrrs.thirdservice.controller;

import com.yuqi.mrrs.thirdservice.oss.OssUtil;
import com.yuqi.mrrs.thirdservice.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/thirdservice")
public class OssController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/imageUpload")
    public String upload( MultipartFile file, @RequestParam("imageType") String type) {
//        String type = uploadData.get("imageType");
        return uploadService.ossUpload(file,type);
    }

    @GetMapping("/third")
    public String test(){
        return "third";
    }
}
