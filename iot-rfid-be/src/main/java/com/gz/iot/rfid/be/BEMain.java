package com.gz.iot.rfid.be;

import com.gz.iot.rfid.core.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.gz.iot.rfid.be", "com.gz.iot.rfid.core"})
public class BEMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BEMain.class, args);
        NettyServer server = ctx.getBean(NettyServer.class);
        server.start();
    }
}
