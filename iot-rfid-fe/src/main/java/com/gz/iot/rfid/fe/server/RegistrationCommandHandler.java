package com.gz.iot.rfid.fe.server;

import com.gz.iot.rfid.core.enums.Command;
import com.gz.iot.rfid.core.packet.HeaderSegment;
import com.gz.iot.rfid.core.packet.RegistrationSegment;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author luojie
 * @createTime 2023/03/04 22:27
 * @description 注册命令处理器
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class RegistrationCommandHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        log.info("RegistrationCommandHandler channelRead0 readableBytes: {}", byteBuf.readableBytes());
        // 解析数据包报文头
        HeaderSegment header = new HeaderSegment(byteBuf);
        // 校验指令码，如果是注册指令则处理，否则丢弃
        if (Command.REGISTRATION == header.getCommand()) {
            RegistrationSegment registrationSegment = new RegistrationSegment(byteBuf);
            // 响应注册指令
            

            ctx.writeAndFlush(null);
        } else {
            byteBuf.release();
        }
    }
}
