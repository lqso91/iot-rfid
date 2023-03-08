package com.gz.iot.rfid.fe.handler;

import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.enums.RegisterResult;
import com.gz.iot.rfid.core.enums.Version;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.Packet;
import com.gz.iot.rfid.core.packet.body.RegisterAckSegment;
import com.gz.iot.rfid.core.utils.SerialNumberUtils;
import com.gz.iot.rfid.core.utils.VerificationUtils;
import com.gz.iot.rfid.fe.config.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
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
    private void add() {
        handlerContainer.put(Command.REGISTER, this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("RegisterCommandHandler channelRead0, {}", packet.getBodySegment().toString());

        ByteBuf ackByteBuf = ByteBufAllocator.DEFAULT.buffer();
        // 起始标识
        ackByteBuf.writeShort(nettyConfig.getPacketMagicNumber());
        // 报文头
        HeaderSegment headerAck = new HeaderSegment();
        headerAck.setCommand(Command.REGISTER_ACK);
        headerAck.setLength(HeaderSegment.SEGMENT_LENGTH + RegisterAckSegment.SEGMENT_LENGTH);
        headerAck.setSerialNumber(SerialNumberUtils.next());
        headerAck.setVersion(Version.V_2_0);
        headerAck.setSecurityCode(0x8000);
        headerAck.setDeviceID(packet.getHeaderSegment().getDeviceID());
        ackByteBuf.writeBytes(headerAck.toByteBuf());
        // 报文体
        RegisterAckSegment registerAck = new RegisterAckSegment();
        registerAck.setRegisterResult(RegisterResult.SUCCESS);
        registerAck.setDateTime(LocalDateTime.now());
        // TODO 从BE注册列表中获取
        registerAck.setIp("192.168.0.103");
        registerAck.setPort(8888);
        ackByteBuf.writeBytes(registerAck.toByteBuf());
        // 校验
        ackByteBuf.readUnsignedShort();
        byte[] data = new byte[ackByteBuf.readableBytes()];
        ackByteBuf.readBytes(data);
        ackByteBuf.resetReaderIndex();
        ackByteBuf.writeShort(VerificationUtils.crc16(data));
        // 输出
        ctx.writeAndFlush(ackByteBuf);
    }
}
