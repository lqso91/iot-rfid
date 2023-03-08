package com.gz.iot.rfid.fe;

import com.gz.iot.rfid.fe.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FEMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FEMain.class, args);
        NettyServer server = ctx.getBean(NettyServer.class);
        server.start();
    }
}
