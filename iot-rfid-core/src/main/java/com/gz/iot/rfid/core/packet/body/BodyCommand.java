package com.gz.iot.rfid.core.packet.body;

import com.gz.iot.rfid.core.enums.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luojie
 * @createTime 2023/03/08 0:01
 * @description 报文体指令注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BodyCommand {
    Command value() default Command.UNKNOWN;
}
