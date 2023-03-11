package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.TLVType;
import com.gz.iot.rfid.core.tlv.TLV;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/11 15:40
 * @description 数据上报指令报文体
 * 数据上报报文体包括若干TLV。
 */
@Data
@BodyCommand(Command.DATA)
public class DataSegment implements IBodySegment {
    /**
     * 设备工作状态（2字节）
     */
    private List<TLV> tlvList;

    @Override
    public IBodySegment parse(ByteBuf byteBuf) {
        this.tlvList = new ArrayList<>();

        // 最后2字节为校验码
        while (byteBuf.readableBytes() > 2) {
            TLV tlv = new TLV();
            tlv.parse(byteBuf);
            tlvList.add(tlv);

            // 出现异常数据退出循环
            if (tlv.getType() == TLVType.UNKNOWN || tlv.getLength() != tlv.getType().getLength()) {
                break;
            }
        }

        return this;
    }
}
