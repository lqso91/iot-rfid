package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/05 23:53
 * @description 设备描述码（高字节）-设备类型
 */
@ToString
public enum DeviceType {
    DATA_GATEWAY(0x01, "数据网关"),
    RFID_READER(0x02, "RFID读头"),
    COMPUTER(0x03, "计算机"),
    UNKNOWN(0x00, "未知");

    private final int code;
    private final String name;

    DeviceType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceType fromCode(int code) {
        for (DeviceType item : DeviceType.values()) {
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
