package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/08 21:45
 * @description 登录结果
 */
@ToString
public enum LoginResult {
    SUCCESS(0x00, "登录成功，无操作请求"),
    SUCCESS_UPDATE_FW(0x02, "登录成功，要求更新主机固件"),
    SUCCESS_REPORT_INFO(0x03, "登录成功，要求上传设备硬件信息"),
    SUCCESS_UPDATE_CONFIG(0x10, "登录成功，要求更新用户配置参数"),
    ERROR(0xFE, "登录错误"),
    DENIED(0xFF, "登录拒绝（设备收到拒绝登录消息，3分钟后再次发送注册消息）"),
    UNKNOWN(0x99, "未知");

    private final int code;
    private final String description;

    LoginResult(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static LoginResult fromCode(int code) {
        for (LoginResult item : LoginResult.values()) {
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
