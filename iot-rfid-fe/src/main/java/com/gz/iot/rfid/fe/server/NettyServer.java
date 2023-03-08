package com.gz.iot.rfid.fe.server;

import com.gz.iot.rfid.fe.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luojie
 * @createTime 2023/03/04 18:32
 * @description FE Netty Server
 */
@Slf4j
@Component
public class NettyServer {

    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private ServerInitializer serverInitializer;

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverInitializer);
            b.bind(nettyConfig.getPort()).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("An exception occurred during server startup", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
