package com.gz.iot.rfid.be.server;

import com.gz.iot.rfid.be.config.NettyConfig;
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
 * @createTime 2023/03/05 00:45
 * @description 检查处理器，检查起始标识与校验码
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class CheckHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Autowired
    private NettyConfig nettyConfig;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        log.info("CheckHandler channelRead0 readableBytes: {}", byteBuf.readableBytes());

        // 校验数据包起始标识，不正确的话，则丢弃所有数据
        int magicNumber = byteBuf.readUnsignedShort();
        if (magicNumber == nettyConfig.getPacketMagicNumber()) {
            // 处理数据包校验码
            byte[] bytes = new byte[byteBuf.readableBytes() - 2];
            byteBuf.readBytes(bytes);

            int crc16 = VerificationUtils.crc16(bytes);
            if (crc16 == byteBuf.readUnsignedShort()) {
                // 重置读索引
                byteBuf.resetReaderIndex();
                byteBuf.readUnsignedShort();
                ctx.fireChannelRead(byteBuf.retain());
            } else {
                log.error("CheckHandler verification failed");
            }
        } else {
            log.info("CheckHandler channelRead0 unknown data");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("CheckHandler channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("CheckHandler exceptionCaught: ", cause);
    }
}
