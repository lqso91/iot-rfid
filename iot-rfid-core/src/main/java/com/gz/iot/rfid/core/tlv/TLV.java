package com.gz.iot.rfid.core.tlv;

import com.gz.iot.rfid.core.enums.TLVType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/11 15:42
 * @description 标签数据段
 */
@Data
public class TLV {
    /**
     * 标签类型（2字节）
     */
    private TLVType type;

    /**
     * 标签数据长度（2字节）
     */
    private int length;

    /**
     * 标签数据（长度由length定义）
     */
    private byte[] value;

    public void parse(ByteBuf byteBuf) {
        if (byteBuf.isReadable(2)) {
            this.type = TLVType.fromCode(byteBuf.readUnsignedShort());
        } else {
            this.type = TLVType.UNKNOWN;
        }

        if (byteBuf.isReadable(2)) {
            this.length = byteBuf.readUnsignedShort();
        }
        if (byteBuf.isReadable(this.length)) {
            this.value = new byte[this.length];
            byteBuf.readBytes(this.value);
        }
    }
}
