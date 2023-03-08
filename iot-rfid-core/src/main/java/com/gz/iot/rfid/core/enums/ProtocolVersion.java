package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/04 21:26
 * @description 报文协议版本
 */
@ToString
public enum ProtocolVersion {
    V_1_0(0x0100, "V1.0"),
    V_2_0(0x0200, "V2.0"),
    UNKNOWN(0x0000, "未知");

    private final int code;
    private final String description;

    ProtocolVersion(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ProtocolVersion fromCode(int code) {
        for (ProtocolVersion item : ProtocolVersion.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
