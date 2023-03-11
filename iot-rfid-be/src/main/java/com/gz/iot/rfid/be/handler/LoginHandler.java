package com.gz.iot.rfid.be.handler;

import com.gz.iot.rfid.core.config.NettyConfig;
import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.LoginResult;
import com.gz.iot.rfid.core.enums.ProtocolVersion;
import com.gz.iot.rfid.core.packet.AckPacket;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.Packet;
import com.gz.iot.rfid.core.packet.body.ack.LoginAckSegment;
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
 * @createTime 2023/03/08 21:25
 * @description 处理登录指令
 * 设备如果登录不成功（没有收到正确的回应），设备会重复发送10次登录请求消息（命令码0x0001），
 * 10次登录不成功后，会重新向负载均衡服务器（注册服务器），发送注册消息。
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<Packet> {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private HandlerContainer handlerContainer;

    @PostConstruct
    private void addHandler() {
        handlerContainer.put(Command.LOGIN, this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("LoginHandler channelRead0, {}", packet.toString());

        AckPacket ackPacket = new AckPacket();

        // 报文头
        HeaderSegment header = new HeaderSegment();
        header.setCommand(Command.LOGIN_ACK);
        header.setLength(HeaderSegment.SEGMENT_LENGTH + LoginAckSegment.SEGMENT_LENGTH);
        header.setSerialNumber(SerialNumberUtils.next());
        header.setProtocolVersion(ProtocolVersion.V_2_0);
        header.setSecurityCode(0x0000);
        header.setDeviceID(packet.getHeaderSegment().getDeviceID());
        ackPacket.setHeaderSegment(header);

        // 报文体
        LoginAckSegment loginAck = new LoginAckSegment();
        loginAck.setLoginResult(LoginResult.SUCCESS);
        loginAck.setDateTime(LocalDateTime.now());
        ackPacket.setBodySegment(loginAck);

        ctx.fireChannelRead(ackPacket);
    }
}