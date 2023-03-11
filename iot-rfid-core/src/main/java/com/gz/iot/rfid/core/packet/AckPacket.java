package com.gz.iot.rfid.core.packet;

import com.gz.iot.rfid.core.packet.body.ack.IBodyAckSegment;
import lombok.Data;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/08 23:29
 * @description 确认数据包
 */
@Data
public class AckPacket {
//    /**
//     * 起始标识
//     */
//    private int magicNumber;
    /**
     * 报文头
     */
    private HeaderSegment headerSegment;
    /**
     * 确认报文体
     */
    private IBodyAckSegment bodySegment;
//    /**
//     * 校验值
//     */
//    private int verificationCode;
}
