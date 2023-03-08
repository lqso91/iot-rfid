package com.gz.iot.rfid.core.packet;

import com.gz.iot.rfid.core.packet.body.IBodySegment;
import lombok.Data;

/**
 * @author luojie
 * @createTime 2023/03/07 21:35
 * @description 数据包
 */
@Data
public class Packet {
    /**
     * 起始标识
     */
    private int magicNumber;
    /**
     * 报文头
     */
    private HeaderSegment headerSegment;
    /**
     * 报文体
     */
    private IBodySegment bodySegment;
    /**
     * 校验值
     */
    private int verificationCode;
}
