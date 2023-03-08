package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/07 00:54
 * @description 注册结果
 */
@ToString
public enum RegisterResult {
    SUCCESS(0x0100, "注册成功"),
    ERROR(0x0200, "注册错误"),
    DENIED(0x0200, "注册拒绝"),
    UNKNOWN(0x0000, "未知");

    private final int code;
    private final String description;

    RegisterResult(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RegisterResult fromCode(int code) {
        for (RegisterResult item : RegisterResult.values()) {
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
