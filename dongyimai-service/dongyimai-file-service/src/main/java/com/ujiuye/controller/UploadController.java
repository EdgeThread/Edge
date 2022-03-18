package com.ujiuye.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.dongyimaicommon.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    String url;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file){
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf(".")+1);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:tracker_client.conf");
            String uploadFile = fastDFSClient.uploadFile(file.getBytes(), extName,null);
            return new Result(true, StatusCode.OK,"上传成功",url+uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"失败",null);
        }
    }
}
