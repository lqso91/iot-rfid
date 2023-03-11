package com.gz.iot.rfid.core.packet.body.ack;

import lombok.Data;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/11 16:05
 * @description 数据上报确认指令报文体
 * 与心跳包（0x8003）报文体相同。
 */
@Data
public class DataAckSegment extends HeartbeatAckSegment {
}
