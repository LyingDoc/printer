package com.tk.lyin.domain;

import com.tk.lyin.utils.CollectionUtils;
import com.tk.lyin.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum LibCpusStatus {

    // 基础就绪状态
    READY("none", "准备就绪 (IDLE)"),

    // 暂停与流程控制
    PAUSED("paused", "暂停 (PAUSED)"),
    MOVING_TO_PAUSED("moving-to-paused", "正在转换到暂停（moving-to-paused）"),
    SHUTDOWN("shutdown", "关机（shutdown）"),

    // 连接与处理
    CONNECTING("connecting-to-device", "连接中（connecting-to-device）"),
    PROCESSING("processing", "处理中（processing）"),
    WARMING_UP("warming-up", "预热中 (warming-up)"),

    // 硬件错误与警告
    SERVICE_REQUESTED("service-requested-error", "维修错误（service-requested-error）"),
    PAPER_OUT("media-empty-error", "纸尽 (media-empty-error)"),
    PAPER_JAM("media-jam-error", "卡纸 (media-jam-error)"),
    DOOR_OPEN("door-open-error", "机盖打开 (door-open-error)"),
    OFFLINE("offline-error", "脱机 (offline-error)"),
    TONER_LOW("toner-low-warning", "墨粉低 (toner-low-warning)"),
    TONER_EMPTY("toner-empty-error", "无墨粉 (toner-empty-error)"),
    OUTPUT_FULL("output-area-full-error", "出纸槽满 (output-area-full-error)");

    private final String bit;
    private final String description;

    /**
     * 解析方法：遍历枚举匹配 CUPS 返回的 reasons 字符串
     *
     * @param status 原始状态
     * @return 组合后的描述文本
     */
    public static String parseDescription(String status) {
        List<String> activeStates = new ArrayList<>();
        for (LibCpusStatus s : LibCpusStatus.values()) {
            if (StringUtils.equals(status, s.getBit())) {
                activeStates.add(s.getDescription());
            }
        }
        return CollectionUtils.isEmpty(activeStates) ? "未知状态（Unknown Status）" + status : StringUtils.join(activeStates, " | ");
    }
}
