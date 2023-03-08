package com.gz.iot.rfid.fe;

import com.gz.iot.rfid.core.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.gz.iot.rfid.fe", "com.gz.iot.rfid.core"})
public class FEMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FEMain.class, args);
        NettyServer server = ctx.getBean(NettyServer.class);
        server.start();
    }
}
