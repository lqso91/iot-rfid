package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/05 23:56
 * @description 设备描述码（低字节）-设备型号
 */
@ToString
public enum DeviceModel {
    MR7901(0x01, "MR7901"),
    MR7901P(0x02, "MR7901P（电动车/WiFi探针）"),
    MR7902(0x03, "MR7902"),
    MR7901K(0x04, "MR7901K（校园考勤）"),
    HX402(0x05, "HX402"),
    MR3202E(0x06, "MR3202E（资产管理）"),
    HT101(0x07, "HT101"),
    MR7901K_1(0x08, "MR7901K-1（一体机电动车方向判断）"),
    MR7901P_1(0x09, "MR7901P-1（读取MR1054x标签）"),
    MR7905(0x0A, "MR7905（电动车入户管理，2天线判断进出）"),
    MR7903(0x0B, "MR7903（电动车入户管理，输出控制脉冲）"),
    HX102(0x10, "HX102（测温考勤一体机）"),
    MR7901U(0x11, "MR7901U（华为USB 2.4G 天线，读取动物标签）"),
    UNKNOWN(0x00, "未知");

    private final int code;
    private final String name;

    DeviceModel(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceModel fromCode(int code) {
        for (DeviceModel item : DeviceModel.values()) {
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
