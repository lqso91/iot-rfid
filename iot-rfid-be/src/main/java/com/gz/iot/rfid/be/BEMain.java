package com.gz.iot.rfid.be;

import com.gz.iot.rfid.be.server.BEServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BEMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BEMain.class, args);
        BEServer server = ctx.getBean(BEServer.class);
        server.start();
    }
}
