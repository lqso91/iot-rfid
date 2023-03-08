package com.gz.iot.rfid.core.packet;

import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 * @author luojie
 * @createTime 2023/03/04 21:07
 * @description 数据包报文头
 */
@Data
public class HeaderSegment {
    public static final int SEGMENT_LENGTH = 28;
    /**
     * 报文总长度（2字节），含报文头与报文体的字节数（不含起始标识符与校验）
     */
    private int length;

    /**
     * 命令码（2字节），表示该报文所要执行或应答的命令，如登录、数据上报、更新等。
     */
    private Command command;

    /**
     * 报文流水号（4字节），0x00000000到0xFFFFFFFF，发送方各自维护自己的流水号，
     * 每次成功的通信后，自动加1，到0xFFFFFFFF后，变为0x00000000。
     */
    private int serialNumber;

    /**
     * 报文协议版本（2字节），V2.0 （专用于MR7902）
     */
    private ProtocolVersion protocolVersion;

    /**
     * 报文安全标识（2字节），不加密的报文默认为0x0000
     */
    private int securityCode;

    /**
     * 设备ID（16字节），16位ASCII码
     */
    private String deviceID;

    public HeaderSegment() {
    }

    public HeaderSegment(ByteBuf byteBuf) {
        if (byteBuf.isReadable(2)) {
            this.length = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(2)) {
            this.command = Command.fromCode(byteBuf.readUnsignedShort());
        }
        if (byteBuf.isReadable(4)) {
            this.serialNumber = byteBuf.readInt();
        }
        if (byteBuf.isReadable(2)) {
            this.protocolVersion = ProtocolVersion.fromCode(byteBuf.readUnsignedShort());
        }
        if (byteBuf.isReadable(2)) {
            this.securityCode = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(16)) {
            this.deviceID = byteBuf.readCharSequence(16, StandardCharsets.US_ASCII).toString();
        }
    }

    public ByteBuf toByteBuf() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(SEGMENT_LENGTH);
        byteBuf.writeShort(length);
        byteBuf.writeShort(command.getCode());
        byteBuf.writeInt(serialNumber);
        byteBuf.writeShort(protocolVersion.getCode());
        byteBuf.writeShort(securityCode);
        byteBuf.writeBytes(deviceID.getBytes(StandardCharsets.US_ASCII));
        return byteBuf;
    }
}