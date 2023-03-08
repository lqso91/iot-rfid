package com.gz.iot.rfid.core.enums;

import lombok.ToString;

/**
 * @author luojie
 * @createTime 2023/03/04 21:11
 * @description 命令码与确认码，依据《MR7901与平台的通信协议_V1.19》 10.指令汇总
 */
@ToString
public enum Command {
    REGISTER(0x0008, "终端注册请求"),
    REGISTER_ACK(0x8008, "平台确认终端注册"),
    LOGIN(0x0001, "终端登录请求"),
    LOGIN_ACK(0x8001, "平台确认终端登录"),
    HEARTBEAT(0x0003, "终端发送心跳"),
    HEARTBEAT_ACK(0x8003, "平台确认心跳"),
    DATA(0x0004, "终端发送标签数据"),
    DATA_ACK(0x8004, "平台确认收到标签数据"),
    FW_UPDATE(0x000D, "终端请求升级固件"),
    FW_UPDATE_ACK(0x800D, "平台确认升级固件"),
    PARM_CONFIG(0x000A, "终端上报配置参数"),
    PARM_CONFIG_ACK(0x800A, "平台确认配置参数"),
    TAGMSG_REQ(0x0009, "终端请求标签消息"),
    TAGMSG_REQ_ACK(0x8009, "平台回应标签消息"),
    SERVER_REQ(0x0005, "平台下发请求"),
    SERVER_REQ_ACK(0x8005, "终端回复请求"),
    UNKNOWN(0x0000, "未知");

    /**
     * 命令码值
     */
    private final int code;

    /**
     * 命令名称
     */
    private final String description;

    Command(int code,String description) {
        this.code = code;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
