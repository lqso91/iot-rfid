package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.DeviceModel;
import com.gz.iot.rfid.core.enums.DeviceType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author luojie
 * @createTime 2023/03/05 23:44
 * @description 注册指令报文体
 */
@Data
@BodyCommand(Command.REGISTER)
public class RegisterSegment implements IBodySegment {
    /**
     * 设备描述码 高字节为设备类型（1字节）
     */
    private DeviceType deviceType;

    /**
     * 设备描述码 低字节为设备型号编码（1字节）
     */
    private DeviceModel deviceModel;

    /**
     * 注册码（4字节）
     */
    private int registerCode;

    @Override
    public IBodySegment parse(ByteBuf byteBuf) {
        if (byteBuf.isReadable(1)) {
            this.deviceType = DeviceType.fromCode(byteBuf.readByte());
        } else {
            this.deviceType = DeviceType.UNKNOWN;
        }
        if (byteBuf.isReadable(1)) {
            this.deviceModel = DeviceModel.fromCode(byteBuf.readByte());
        } else {
            this.deviceModel = DeviceModel.UNKNOWN;
        }
        if (byteBuf.isReadable(4)) {
            this.registerCode = byteBuf.readInt();
        }
        return this;
    }
}
