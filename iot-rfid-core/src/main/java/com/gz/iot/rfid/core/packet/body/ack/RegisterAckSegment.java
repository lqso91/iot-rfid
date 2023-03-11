package com.gz.iot.rfid.core.packet.body.ack;

import com.gz.iot.rfid.core.enums.RegisterResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author luojie
 * @createTime 2023/03/07 00:50
 * @description 确认注册指令报文体
 */
@Data
public class RegisterAckSegment implements IBodyAckSegment {
    public static final int SEGMENT_LENGTH = 41;

    /**
     * 注册结果（1字节）
     */
    private RegisterResult registerResult;

    /**
     * 实时时间（6字节）
     */
    private LocalDateTime dateTime;

    /**
     * 负载服务器IP（32字节）
     */
    private String ip;

    /**
     * 负载服务器端口（2字节）
     */
    private int port;

    @Override
    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(SEGMENT_LENGTH);
        // 注册结果
        byteBuf.writeByte(registerResult.getCode());
        // 时间：年（x-2000）月日时分秒
        byteBuf.writeByte(dateTime.getYear() - 2000);
        byteBuf.writeByte(dateTime.getMonthValue());
        byteBuf.writeByte(dateTime.getDayOfMonth());
        byteBuf.writeByte(dateTime.getHour());
        byteBuf.writeByte(dateTime.getMinute());
        byteBuf.writeByte(dateTime.getSecond());
        // 负载服务器IP
        byte[] ipBytes = ip.getBytes(StandardCharsets.US_ASCII);
        for (int i = 0; i < 32; i++) {
            byteBuf.writeByte(i < ipBytes.length ? ipBytes[i] : 0);
        }
        // 负载服务器端口
        byteBuf.writeByte(port & 0x00FF);
        byteBuf.writeByte((port & 0xFF00) >> 8);
        return byteBuf;
    }
}
