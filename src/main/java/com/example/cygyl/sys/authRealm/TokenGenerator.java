package com.example.cygyl.sys.authRealm;
import com.example.cygyl.exception.TokenException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * @description: 生成Token
 * @author: Tanglie
 * @time: 2021/3/9
 */
public class TokenGenerator {
    public static String generateValue(){

        return generateValue(UUID.randomUUID().toString());
    }

    private static char[] hexCode = "0123456789abcdef".toCharArray();

    private static String toHeString(byte[] data) {
        if (data == null){
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b :data){
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[b & 0xf]);
        }
        return r.toString();
    }


    private static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest =algorithm.digest();
            return toHeString(messageDigest);
        } catch (Exception e) {
            throw new TokenException("生成Token失败",e);
        }
    }

}
