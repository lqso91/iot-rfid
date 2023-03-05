package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/04 21:11
 * @description 命令码与响应码
 */
@ToString
public enum Command {
    REGISTRATION(0x0008, 0x8008, "注册"),
    UNKNOWN(0x0000, 0x0000, "未知");

    /**
     * 命令码值
     */
    private final int code;
    /**
     * 响应命令码值
     */
    private final int responseCode;
    /**
     * 命令名称
     */
    private final String name;

    Command(int code, int responseCode, String name) {
        this.code = code;
        this.responseCode = responseCode;
        this.name = name;
    }

    public static Command fromCode(int code) {
        for (Command item : Command.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getName() {
        return name;
    }
}
