package com.gz.iot.rfid.core.packet.body;

import io.netty.buffer.ByteBuf;

/**
 * @author luojie
 * @createTime 2023/03/07 23:48
 * @description 确认指令报文体接口
 */
public interface IBodyAckSegment {
    ByteBuf toByteBuf();
}
