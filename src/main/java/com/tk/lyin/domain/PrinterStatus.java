package com.tk.lyin.domain;

import com.tk.lyin.utils.CollectionUtils;
import com.tk.lyin.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum PrinterStatus {
    IDLE(0x00000000, "准备就绪（IDLE）"),
    PAUSED(0x00000001, "暂停（Paused）"),
    ERROR(0x00000002, "错误（Printer Error）"),
    PENDING_DELETION(0x00000004, "正在删除（Pending Deletion）"),
    PAPER_JAM(0x00000008, "塞纸（Paper Jam）"),
    PAPER_OUT(0x00000010, "打印纸用完（Paper Out）"),
    MANUAL_FEED(0x00000020, "手工送纸（Manual Feed）"),
    PAPER_PROBLEM(0x00000040, "纸张问题（Page Problem）"),
    OFFLINE(0x00000080, "脱机（Off Line）"),
    IO_ACTIVE(0x00000100, "正在输入或输出（I/O Active）"),
    BUSY(0x00000200, "忙（Busy）"),
    PRINTING(0x00000400, "正在打印（Printing）"),
    OUTPUT_BIN_FULL(0x00000800, "输出口已满（Output Bin Full）"),
    NOT_AVAILABLE(0x00001000, "不可用（Not Available）"),
    PROCESSING(0x00004000, "正在处理（Processing）"),
    INITIALIZING(0x00008000, "正在初始化（Initializing）"), // 对应 0x8000
    WARMING_UP(0x00010000, "正在准备（Warming Up）"),
    TONER_LOW(0x00020000, "墨粉不足（Toner Low）"),
    NO_TONER(0x00040000, "无墨粉（No Toner）"),
    PAGE_PUNT(0x00080000, "当前页无法打印（Page Punt）"),
    USER_INTERVENTION(0x00100000, "需要用户干预（User Intervention）"),
    OUT_OF_MEMORY(0x00200000, "内存溢出（Out of Memory）"),
    DOOR_OPEN(0x00400000, "门被打开（Printer Door Open）"),
    WAITING(0x20000000, "等待（Waiting）");


    private final int bit;
    private final String description;

    /**
     * 将 Status 位掩码解析为人类可读的中文描述
     *
     * @param status 原始 int 状态码
     * @return 状态描述字符串
     */
    public static String parseDescription(int status) {
        if (status == 0) return "准备就绪（Ready）";
        List<String> activeStates = new ArrayList<>();
        for (PrinterStatus s : PrinterStatus.values()) {
            if ((status & s.getBit()) != 0) {
                activeStates.add(s.getDescription());
            }
        }
        return CollectionUtils.isEmpty(activeStates) ? "未知状态（Unknown Status）" + status : StringUtils.join(activeStates, " | ");
    }

}
