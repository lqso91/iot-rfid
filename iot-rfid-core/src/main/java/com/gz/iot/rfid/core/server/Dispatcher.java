package com.gz.iot.rfid.core.server;

import com.gz.iot.rfid.core.config.NettyConfig;
import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.Packet;
import com.gz.iot.rfid.core.packet.body.BodySegmentParser;
import com.gz.iot.rfid.core.packet.body.IBodySegment;
import com.gz.iot.rfid.core.utils.VerificationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luojie
 * @createTime 2023/03/04 18:32
 * @description 指令调度，检查起始标识与校验码
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class Dispatcher extends SimpleChannelInboundHandler<ByteBuf> {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private HandlerContainer handlerContainer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        log.info("Dispatcher channelRead0 readableBytes: {}", byteBuf.readableBytes());

        // 校验数据包起始标识
        int magicNumber = byteBuf.readUnsignedShort();
        if (magicNumber != nettyConfig.getPacketMagicNumber()) {
            log.error("Dispatcher channelRead0 unknown data");
            return;
        }

        // 校验数据包校验码
        byte[] bytes = new byte[byteBuf.readableBytes() - 2];
        byteBuf.readBytes(bytes);
        int verificationCode = VerificationUtils.crc16(bytes);
        if (verificationCode != byteBuf.readUnsignedShort()) {
            log.error("Dispatcher verification failed");
            return;
        }

        // 解析报文头
        byteBuf.resetReaderIndex();
        byteBuf.readUnsignedShort();
        HeaderSegment header = new HeaderSegment(byteBuf);
        log.info(header.toString());

        // 处理指令
        if (!handlerContainer.contains(header.getCommand())) {
            log.error("Dispatcher not found {} handler", header.getCommand().name());
            return;
        }
        for (Command command : handlerContainer.commands()) {
            ChannelHandler channelHandler = ctx.pipeline().get(command.name());
            if (channelHandler != null) {
                ctx.pipeline().remove(channelHandler);
            }
        }
        ctx.pipeline().addAfter("DISPATCHER", header.getCommand().name(), handlerContainer.get(header.getCommand()));
        
        Packet packet = new Packet();
        packet.setMagicNumber(magicNumber);
        packet.setHeaderSegment(header);
        packet.setVerificationCode(verificationCode);

        IBodySegment bodySegment = BodySegmentParser.parse(header.getCommand(), byteBuf);
        if (bodySegment != null) {
            log.info(bodySegment.toString());
            packet.setBodySegment(bodySegment);
            ctx.fireChannelRead(packet);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Dispatcher channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Dispatcher exceptionCaught: ", cause);
    }
}
