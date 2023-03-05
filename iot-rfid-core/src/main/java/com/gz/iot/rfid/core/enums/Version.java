package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/04 21:26
 * @description 报文协议版本
 */
@ToString
public enum Version {
    V_1_0(0x0100, "V1.0"),
    V_2_0(0x0200, "V2.0"),
    UNKNOWN(0x0000, "未知");

    private final int code;
    private final String name;

    Version(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Version fromCode(int code) {
        for (Version item : Version.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
