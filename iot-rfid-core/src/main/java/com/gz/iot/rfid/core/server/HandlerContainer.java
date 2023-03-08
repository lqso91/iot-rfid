package com.gz.iot.rfid.core.server;

import com.gz.iot.rfid.core.enums.Command;
import io.netty.channel.ChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author luojie
 * @createTime 2023/03/07 22:26
 * @description 指令处理器容器
 */
@Component
public class HandlerContainer {
    private final Map<Command, ChannelInboundHandler> map = new HashMap<>();

    public void put(Command command, ChannelInboundHandler handler) {
        map.put(command, handler);
    }

    public ChannelInboundHandler get(Command command) {
        return map.get(command);
    }

    public boolean contains(Command command) {
        return map.containsKey(command);
    }
    
    public Set<Command> commands() {
        return map.keySet();
    }
}
