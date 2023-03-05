package com.gz.iot.rfid.fe.server;

import com.gz.iot.rfid.fe.config.NettyConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luojie
 * @createTime 2023/03/04 18:32
 * @description 初始化通道
 */
@Slf4j
@Component
public class FEServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private CheckHandler checkHandler;

    @Autowired
    private RegistrationCommandHandler registrationCommandHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        /*
        依据《MR7901与平台的通信协议_V1.19》- 6.通信数据包格式
        格式：    起始标识   报文头   报文体  校验
        数据：    起始标识 报文总长度   ...   校验
        字节数：     2       2        n     2
        报文总长度-含报文头与报文体的字节数（不含起始标识符与校验），报文总长度的值，刚好与之后的字节数相等，因此lengthAdjustment为0
        */
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
                nettyConfig.getMaxFrameLength(),
                nettyConfig.getLengthFieldOffset(),
                nettyConfig.getLengthFieldLength(),
                nettyConfig.getLengthAdjustment(),
                nettyConfig.getInitialBytesToStrip()));
        pipeline.addLast(checkHandler);
        pipeline.addLast(registrationCommandHandler);
        log.info("init channel completed.");
    }
}
