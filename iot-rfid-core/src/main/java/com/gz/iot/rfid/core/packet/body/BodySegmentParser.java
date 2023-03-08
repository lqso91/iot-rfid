package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author luojie
 * @createTime 2023/03/07 23:52
 * @description 报文体解析器
 */
@Slf4j
public class BodySegmentParser {

    private static final Map<Command, Class<? extends IBodySegment>> map = new HashMap<>();

    static {
        String packageName = BodySegmentParser.class.getPackageName();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageName)
                .addScanners(Scanners.SubTypes)
        );
        Set<Class<? extends IBodySegment>> bodySegments = reflections.getSubTypesOf(IBodySegment.class);
        for (Class<? extends IBodySegment> bodySegment : bodySegments) {
            BodyCommand annotation = bodySegment.getAnnotation(BodyCommand.class);
            map.put(annotation.value(), bodySegment);
            log.info("BodySegmentParser added a BodySegment [{}]", bodySegment.getName());
        }
    }

    /**
     * 解析
     *
     * @param command 指令
     * @param byteBuf ByteBuf
     * @return 解析后的BodySegment，可能为null
     */
    public static IBodySegment parse(Command command, ByteBuf byteBuf) {
        if (map.containsKey(command)) {
            try {
                IBodySegment bodySegment = map.get(command).getDeclaredConstructor().newInstance();
                return bodySegment.parse(byteBuf);
            } catch (Exception e) {
                log.error("BodySegmentParser Exception: ", e);
                return null;
            }
        }
        log.info("BodySegmentParser not found {} BodySegment", command.name());
        return null;
    }
}
