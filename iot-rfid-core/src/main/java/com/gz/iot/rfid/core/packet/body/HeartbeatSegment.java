package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/09 17:31
 * @description 心跳指令报文体
 */
@Data
@BodyCommand(Command.HEARTBEAT)
public class HeartbeatSegment implements IBodySegment {
    /**
     * 设备工作状态（2字节）
     */
    private int deviceWorkingState;

    /**
     * 设备状态（2字节）
     */
    private int deviceState;

    /**
     * 设备版本（2字节）
     */
    private int deviceVersion;

    /**
     * 设备时间（6字节）
     */
    private LocalDateTime dateTime;

    @Override
    public IBodySegment parse(ByteBuf byteBuf) {
        if (byteBuf.isReadable(2)) {
            this.deviceWorkingState = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(2)) {
            this.deviceState = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(2)) {
            this.deviceVersion = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(6)) {
            this.dateTime = LocalDateTime.now()
                    .withYear(2000 + byteBuf.readByte())
                    .withMonth(byteBuf.readByte())
                    .withDayOfMonth(byteBuf.readByte())
                    .withHour(byteBuf.readByte())
                    .withMinute(byteBuf.readByte())
                    .withSecond(byteBuf.readByte())
                    .withNano(0);
        }
        return this;
    }
}
