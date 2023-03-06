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
    CODE_ERROR(0x0200, "注册码错误"),
    DENIED(0x0200, "注册拒绝"),
    UNKNOWN(0x0000, "未知");

    private final int code;
    private final String name;

    RegisterResult(int code, String name) {
        this.code = code;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
