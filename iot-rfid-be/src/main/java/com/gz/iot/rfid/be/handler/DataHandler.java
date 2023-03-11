package com.gz.iot.rfid.be.handler;

import com.gz.iot.rfid.core.config.NettyConfig;
import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.OperationType;
import com.gz.iot.rfid.core.enums.ProtocolVersion;
import com.gz.iot.rfid.core.packet.AckPacket;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.Packet;
import com.gz.iot.rfid.core.packet.body.ack.DataAckSegment;
import com.gz.iot.rfid.core.server.HandlerContainer;
import com.gz.iot.rfid.core.utils.SerialNumberUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/11 16:08
 * @description 处理数据上报指令
 * 设备如果发送数据消息，但没有收到平台正确回应消息，会重复发送6次数据消息（命令码0x0004），
 * 6次后，会重新向负载服务发送登录消息（命令码0x0001）。
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class DataHandler extends SimpleChannelInboundHandler<Packet> {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private HandlerContainer handlerContainer;

    @PostConstruct
    private void addHandler() {
        handlerContainer.put(Command.DATA, this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("DataHandler channelRead0, {}", packet.toString());

        AckPacket ackPacket = new AckPacket();

        // 报文头
        HeaderSegment header = new HeaderSegment();
        header.setCommand(Command.DATA_ACK);
        header.setLength(HeaderSegment.SEGMENT_LENGTH + DataAckSegment.SEGMENT_LENGTH);
        header.setSerialNumber(SerialNumberUtils.next());
        header.setProtocolVersion(ProtocolVersion.V_2_0);
        header.setSecurityCode(0x0000);
        header.setDeviceID(packet.getHeaderSegment().getDeviceID());
        ackPacket.setHeaderSegment(header);

        // 报文体
        DataAckSegment dataAck = new DataAckSegment();
        dataAck.setOperationType(OperationType.OT00);
        dataAck.setDateTime(LocalDateTime.now());
        ackPacket.setBodySegment(dataAck);

        ctx.fireChannelRead(ackPacket);
    }
}