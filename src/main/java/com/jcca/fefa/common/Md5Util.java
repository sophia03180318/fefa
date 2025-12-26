package com.jcca.fefa.common;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/24 17:11
 **/
import java.security.MessageDigest;

public class Md5Util {

    /**
     * MD5 加密（32位小写）
     */
    public static String encrypt(String source) {
        if (source == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(source.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    /**
     * 校验密码
     *
     * @param plainText 明文
     * @param md5Text   MD5 密文
     * @return 是否匹配
     */
    public static boolean verify(String plainText, String md5Text) {
        if (plainText == null || md5Text == null) {
            return false;
        }
        return encrypt(plainText).equals(md5Text);
    }
}
