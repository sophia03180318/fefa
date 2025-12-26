package com.jcca.fefa.common;

import java.util.Random;

/**
 * @ClassName MyIdUtil
 * @Description ID生成器
 * @Date 2020/4/7 16:02
 * @Author hanwone
 */
public class MyIdUtil {

    /**
     * 返回唯一数字ID
     *
     * @return
     */
    public static String getId() {
        long timestamp = System.currentTimeMillis(); // 13位时间戳
        int random = new Random().nextInt(900000) + 100000; // 6位随机数
        return timestamp + String.valueOf(random);
    }

}
