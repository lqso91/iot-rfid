package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author luojie
 * @createTime 2023/03/08 21:34
 * @description 登录指令报文体
 */
@Data
@BodyCommand(Command.LOGIN)
public class LoginSegment implements IBodySegment {
    /**
     * 设备软件版本（2字节）
     */
    private int version;

    /**
     * 配置参数的CRC16校验（2字节）
     */
    private int verificationCode;

    @Override
    public IBodySegment parse(ByteBuf byteBuf) {
        if (byteBuf.isReadable(2)) {
            this.version = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(2)) {
            this.verificationCode = byteBuf.readUnsignedShort();
        }
        return this;
    }
}
