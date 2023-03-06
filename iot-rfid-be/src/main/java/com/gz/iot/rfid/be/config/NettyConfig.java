package com.gz.iot.rfid.be.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author luojie
 * @createTime 2023/03/05 00:45
 * @description Netty 配置项
 */
@Configuration
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    /**
     * netty端口
     */
    private int port = 8888;
    /**
     * 数据包起始标识
     */
    private int packetMagicNumber = 0x55aa;
    /**
     * LengthFieldBasedFrameDecoder maxFrameLength
     */
    private int maxFrameLength = 10240;
    /**
     * LengthFieldBasedFrameDecoder lengthFieldOffset
     */
    private int lengthFieldOffset = 2;
    /**
     * LengthFieldBasedFrameDecoder lengthFieldLength
     */
    private int lengthFieldLength = 2;
    /**
     * LengthFieldBasedFrameDecoder lengthAdjustment
     */
    private int lengthAdjustment = 0;
    /**
     * LengthFieldBasedFrameDecoder initialBytesToStrip
     */
    private int initialBytesToStrip = 0;
}