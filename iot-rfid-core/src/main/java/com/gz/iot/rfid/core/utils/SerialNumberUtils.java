package com.gz.iot.rfid.core.utils;

/**
 * @author luojie
 * @createTime 2023/03/06 0:27
 * @description 生成报文流水号工具类
 * 0x00000000到0xFFFFFFFF，发送方各自维护自己的流水号，每次成功的通信后，自动加1，到0xFFFFFFFF后，变为0x00000000。
 */
public class SerialNumberUtils {
    private static int value = 0;

    public static int next() {
        // 0xFFFFFFFF(-1) + 1 = 0x00000000(0)
        return value++;
    }
}
