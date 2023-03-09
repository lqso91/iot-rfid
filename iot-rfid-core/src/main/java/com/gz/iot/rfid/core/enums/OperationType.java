package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/09 17:22
 * @description 操作指示
 */
@ToString
public enum OperationType {
    OT00(0x00, "没有操作指示"),
    OT02(0x02, "要求更新主机固件"),
    OT03(0x03, "复位设备(设备收到后，不回应，直接重启)"),
    OT04(0x04, "更新天线固件"),
    OT05(0x05, "获取天线信息(版本、增益、rssi门限)"),
    OT06(0x06, "设置设备时间(设备收到后，设置时间，不回应)"),
    OT07(0x07, "更新上报标签标识"),
    OT08(0x08, "清除缓存标签数据(设备收到后，清除缓存的标签数据，不回应)"),
    OT0A(0x0A, "要求更新加密传输标识（控制是否启用加密）"),
    OT0B(0x0B, "要求更新加密秘钥（必须启用加密后，设备才响应）"),
    OT10(0x10, "要求更新用户配置参数"),
    OT11(0x11, "获取设备状态"),
    OT12(0x12, "要求上传设备硬件信息"),
    OT20(0x20, "要求发送消息到标签"),
    OT21(0x21, "要求上传待发送的标签消息条数"),
    OT22(0x22, "要求清除待发送的标签消息"),
    OT23(0x23, "更新白名单"),
    UNKNOWN(0xFF, "未知");

    private final int code;
    private final String description;

    OperationType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OperationType fromCode(int code) {
        for (OperationType item : OperationType.values()) {
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
