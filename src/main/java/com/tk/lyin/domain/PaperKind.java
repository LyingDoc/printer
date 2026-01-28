package com.tk.lyin.domain;

import java.util.HashMap;
import java.util.Map;

public class PaperKind {
    private static final Map<String, Integer> PAPER_MAP = new HashMap<>();

    static {
        // --- 北美标准 (North American) ---
        PAPER_MAP.put("LETTER", 1);
        PAPER_MAP.put("LEGAL", 5);
        PAPER_MAP.put("EXECUTIVE", 7);
        PAPER_MAP.put("LEDGER", 4);
        PAPER_MAP.put("TABLOID", 3);
        PAPER_MAP.put("TABLOIDEXTRA", 52);
        PAPER_MAP.put("11X14", 115); // DMPAPER_11X14
        PAPER_MAP.put("11X17", 3);   // 同 Tabloid
        PAPER_MAP.put("8X10", 65);
        PAPER_MAP.put("8X12", 256);  // 自定义

        // --- ISO A 系列 ---
        PAPER_MAP.put("4A", 256);    // 超大规格通常映射为 Custom
        PAPER_MAP.put("2A", 256);
        PAPER_MAP.put("A0", 57);
        PAPER_MAP.put("A1", 58);
        PAPER_MAP.put("A2", 59);
        PAPER_MAP.put("A3", 8);
        PAPER_MAP.put("A4", 9);
        PAPER_MAP.put("A5", 11);

        // --- ANSI & ARCH (建筑/工程) ---
        PAPER_MAP.put("ANSIA", 1);   // Letter
        PAPER_MAP.put("ANSIB", 3);   // Tabloid
        PAPER_MAP.put("ANSIC", 24);
        PAPER_MAP.put("ANSID", 25);
        PAPER_MAP.put("ANSIE", 26);
        PAPER_MAP.put("ARCHA", 256);
        PAPER_MAP.put("ARCHB", 256);
        PAPER_MAP.put("ARCHC", 256);
        PAPER_MAP.put("ARCHD", 256);
        PAPER_MAP.put("ARCHE", 256);

        // --- ISO B & JIS B 系列 ---
        PAPER_MAP.put("ISOB0", 256);
        PAPER_MAP.put("ISOB4", 42);
        PAPER_MAP.put("ISOB5", 43);
        PAPER_MAP.put("JISB0", 63);
        PAPER_MAP.put("JISB1", 64);
        PAPER_MAP.put("JISB2", 66);
        PAPER_MAP.put("JISB3", 67);
        PAPER_MAP.put("JISB4", 12);
        PAPER_MAP.put("JISB5", 13);

        // --- ISO C 系列 (信封/封皮) ---
        PAPER_MAP.put("C0", 256);
        PAPER_MAP.put("C1", 256);
        PAPER_MAP.put("C2", 256);
        PAPER_MAP.put("C3", 29);
        PAPER_MAP.put("C4", 30);
        PAPER_MAP.put("C5", 28);

        // --- 信封 (Envelopes) ---
        PAPER_MAP.put("ENV10", 20);
        PAPER_MAP.put("ENVC5", 28);
        PAPER_MAP.put("ENVDL", 27);
        PAPER_MAP.put("ENVMONARCH", 37);

        // --- SRA/RA 印刷系列 ---
        // 多数驱动中 SRA 系列被视为自定义或特定扩展
        PAPER_MAP.put("SRA3", 154);
        PAPER_MAP.put("SUPERB", 143); // 13x19
        PAPER_MAP.put("SUPERA", 256);
    }

    /**
     * 获取纸张编号 (Kind)
     *
     * @param rawName CUPS返回的原始名称 (如 "*A4")
     * @return 对应的DMPAPER ID，匹配不到返回 256 (Custom)
     */
    public static int getKind(String rawName) {
        if (rawName == null || rawName.isEmpty()) return 256;

        // 1. 处理前缀星号和空格
        String key = rawName.replace("*", "").trim().toUpperCase();

        // 2. 查找映射
        return PAPER_MAP.getOrDefault(key, 256);
    }
}
