package com.gz.iot.rfid.fe.handler;

import com.gz.iot.rfid.core.config.NettyConfig;
import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.RegisterResult;
import com.gz.iot.rfid.core.enums.ProtocolVersion;
import com.gz.iot.rfid.core.packet.AckPacket;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.Packet;
import com.gz.iot.rfid.core.packet.body.ack.RegisterAckSegment;
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
 * @createTime 2023/03/04 22:27
 * @description 注册命令处理器
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class RegisterHandler extends SimpleChannelInboundHandler<Packet> {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private HandlerContainer handlerContainer;

    @PostConstruct
    private void addHandler() {
        handlerContainer.put(Command.REGISTER, this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("RegisterHandler channelRead0, {}", packet.toString());

        AckPacket ackPacket = new AckPacket();
        // 报文头
        HeaderSegment header = new HeaderSegment();
        header.setCommand(Command.REGISTER_ACK);
        header.setLength(HeaderSegment.SEGMENT_LENGTH + RegisterAckSegment.SEGMENT_LENGTH);
        header.setSerialNumber(SerialNumberUtils.next());
        header.setProtocolVersion(ProtocolVersion.V_2_0);
        header.setSecurityCode(0x0000);
        header.setDeviceID(packet.getHeaderSegment().getDeviceID());
        ackPacket.setHeaderSegment(header);

        // 报文体
        RegisterAckSegment registerAck = new RegisterAckSegment();
        registerAck.setRegisterResult(RegisterResult.SUCCESS);
        registerAck.setDateTime(LocalDateTime.now());
        // TODO 从BE注册列表中获取
        registerAck.setIp("192.168.0.103");
        registerAck.setPort(8888);
        ackPacket.setBodySegment(registerAck);

        ctx.fireChannelRead(ackPacket);
    }
}
