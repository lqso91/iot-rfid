package com.gz.iot.rfid.fe.server;

import com.gz.iot.rfid.core.utils.VerificationUtils;
import com.gz.iot.rfid.fe.config.NettyConfig;
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

            int crc16 = VerificationUtils.crc16_2(bytes);
            if (crc16 == byteBuf.readUnsignedShort()) {
                ctx.fireChannelRead(byteBuf.resetReaderIndex().readUnsignedShort());
                log.info("校验通过...");
            } else {
                log.error("校验不通过...");
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

/*
[85, -86, 0, 34, 0, 8, 0, 0, 0, 3, 2, 0, 0, 0, 50, 50, 49, 48, 49, 56, 53, 48, 49, 48, 48, 48, 48, 53, 50, 0, 1, 3, 120, 86, 52, 18, 92, -108]
 */
