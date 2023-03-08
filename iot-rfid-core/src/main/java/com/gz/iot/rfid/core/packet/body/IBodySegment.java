package com.gz.iot.rfid.core.packet.body;

import io.netty.buffer.ByteBuf;

/**
 * @author luojie
 * @createTime 2023/03/07 21:34
 * @description 报文体接口
 */
public interface IBodySegment {
    IBodySegment parse(ByteBuf byteBuf);
}
