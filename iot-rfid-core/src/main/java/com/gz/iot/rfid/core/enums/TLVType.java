package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @email 806839696@qq.com
 * @createTime 2023/03/11 16:15
 * @description TLV类型
 */
@ToString
public enum TLVType {
    TLV_8801(0x8801, 17, "RFID物品监控"),
    TLV_8901(0x8901, 16, "电流标签监控"),
    TLV_8A01(0x8A01, 18, "健康手环数据"),
    TLV_8B01(0x8B01, 17, "电子标签"),
    TLV_8B02(0x8B02, 17, "考勤标签"),
    TLV_8B03(0x8B03, 23, "MR1054X标签"),
    TLV_8B04(0x8B04, 19, "电子标签2(带中继地址)"),
    TLV_8B05(0x8B05, 23, "动物标签1"),
    TLV_8B06(0x8B06, 22, "GPS信息"),
    TLV_8B07(0x8B07, 25, "动物标签2"),
    UNKNOWN(0x0000, -1, "未知");

    private final int code;

    private final int length;

    private final String description;

    TLVType(int code, int length, String description) {
        this.code = code;
        this.length = length;
        this.description = description;
    }

    public static TLVType fromCode(int code) {
        for (TLVType item : TLVType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

    public String getDescription() {
        return description;
    }
}
