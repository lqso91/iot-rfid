package com.gz.iot.rfid.core.server;

import com.gz.iot.rfid.core.config.NettyConfig;
import com.gz.iot.rfid.core.packet.AckPacket;
import com.gz.iot.rfid.core.utils.VerificationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/08 23:32
 * @description 回应各种指令
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class Answerer extends SimpleChannelInboundHandler<AckPacket> {

    @Autowired
    private NettyConfig nettyConfig;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AckPacket ackPacket) throws Exception {
        log.info("Answerer channelRead0, {}", ackPacket.toString());

        ByteBuf ackByteBuf = ByteBufAllocator.DEFAULT.buffer();
        // 起始标识
        ackByteBuf.writeShort(nettyConfig.getPacketMagicNumber());
        // 报文头
        ackByteBuf.writeBytes(ackPacket.getHeaderSegment().toByteBuf());
        // 报文体
        ackByteBuf.writeBytes(ackPacket.getBodySegment().toByteBuf());
        // 校验
        ackByteBuf.readUnsignedShort();
        byte[] data = new byte[ackByteBuf.readableBytes()];
        ackByteBuf.readBytes(data);
        ackByteBuf.resetReaderIndex();
        ackByteBuf.writeShort(VerificationUtils.crc16(data));
        // 输出
        ctx.writeAndFlush(ackByteBuf);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Answerer channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Answerer exceptionCaught: ", cause);
    }
}