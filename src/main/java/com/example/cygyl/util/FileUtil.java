package com.example.cygyl.util;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.example.cygyl.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author 黎源
 * @date 2021/3/15 9:29
 */

@Service
public class FileUtil {

    @Autowired
    private ObjectAuthorization objectAuthorization;
    @Autowired
    private ObjectConfig objectConfig;
    @Value("${us3.bucket}")
    private String bucket;

    /**
     * 单个文件上传
     * @param inputStream
     * @param mimeType
     * @param fileName
     * @return
     */
    public String putFile(InputStream inputStream,String mimeType,String fileName) {
        if (!isImage(fileName,new String[]{"jpg","png"})) {
            throw new ForbiddenException(10005);
        }
        String reallyName = getRealName(fileName);
        try {
            PutObjectResultBean resultBean = UfileClient.object(objectAuthorization, objectConfig)
                    .putObject(inputStream, mimeType)
                    .nameAs(reallyName)
                    .toBucket(bucket)
                    .execute();
            if (resultBean != null && resultBean.getRetCode() == 0) {
                return getUrl(reallyName);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
        } catch (UfileServerException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 处理文件名
     * @param fileName
     * @return
     */
    public String getRealName(String fileName) {
        String realName;
        String fileNames[] = fileName.split("\\.");
        if (fileNames.length > 1) {
            realName = UUID.randomUUID().toString() + "." + fileNames[fileNames.length - 1];
            return realName;
        } else {
            throw new ForbiddenException(10005);
        }
    }

    /**
     * 生成带时效的url
     * @param realmName
     * @return
     */
    public String getUrl(String realmName) {
        String url = "";
        try {
            url = UfileClient.object(objectAuthorization, objectConfig)
                    .getDownloadUrlFromPrivateBucket(realmName, bucket, 24 * 24 * 60 * 365 * 20)
                    .createUrl();
            return url;
        } catch (UfileClientException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * 判断图片是否合法
     * @param FileName
     * @param lastNames
     * @return
     */
    public Boolean isImage(String FileName,String lastNames[]) {
      String names[] = FileName.split("\\.");
      String lastName = names[names.length - 1];
      return Arrays.stream(lastNames).anyMatch(lastName::equals);
    }
}
