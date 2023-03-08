package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.LoginResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/08 21:43
 * @description 登录确认指令报文体
 */
@Data
public class LoginAckSegment implements IBodyAckSegment {
    public static final int SEGMENT_LENGTH = 7;

    /**
     * 登录结果（1字节）
     */
    private LoginResult loginResult;

    /**
     * 实时时间（6字节）
     */
    private LocalDateTime dateTime;

    @Override
    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(SEGMENT_LENGTH);
        // 登录结果
        byteBuf.writeByte(loginResult.getCode());
        // 时间：年（x-2000）月日时分秒
        byteBuf.writeByte(dateTime.getYear() - 2000);
        byteBuf.writeByte(dateTime.getMonthValue());
        byteBuf.writeByte(dateTime.getDayOfMonth());
        byteBuf.writeByte(dateTime.getHour());
        byteBuf.writeByte(dateTime.getMinute());
        byteBuf.writeByte(dateTime.getSecond());
        return byteBuf;
    }
}
