package com.example.cygyl.v1.controller;

import com.example.cygyl.core.response.UnifyWithBody;
import com.example.cygyl.exception.ForbiddenException;
import com.example.cygyl.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 黎源
 * @date 2021/3/15 9:49
 */
@RestController
@RequestMapping("/file")
@Api(tags = "图片上传")
public class FileController {

    @Autowired
    private FileUtil fileUtil;

    /**
     * 单图片上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/putFile")
    @ApiOperation("上传单张图片")
    public UnifyWithBody<String> putFile(@RequestParam("myfile") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new ForbiddenException(10005);
        }
        String url = fileUtil.putFile(multipartFile.getInputStream(),
                multipartFile.getContentType(),
                multipartFile.getOriginalFilename());
        return new UnifyWithBody<>(url);
    }
}
