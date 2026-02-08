package com.tk.lyin.domain;

import com.tk.lyin.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PaperKind {
    private static final Map<String, Integer> PAPER_MAP = new HashMap<>();

    static {

        // 标准 DMPAPER 1~118 (WinGDI.h)
        PAPER_MAP.put("LETTER", 1);
        PAPER_MAP.put("LETTERSMALL", 2);
        PAPER_MAP.put("TABLOID", 3);
        PAPER_MAP.put("LEDGER", 4);
        PAPER_MAP.put("LEGAL", 5);
        PAPER_MAP.put("STATEMENT", 6);
        PAPER_MAP.put("EXECUTIVE", 7);
        PAPER_MAP.put("A3", 8);
        PAPER_MAP.put("A4", 9);
        PAPER_MAP.put("A4SMALL", 10);
        PAPER_MAP.put("A5", 11);
        PAPER_MAP.put("B4", 12);
        PAPER_MAP.put("B5", 13);
        PAPER_MAP.put("FOLIO", 14);
        PAPER_MAP.put("QUARTO", 15);
        PAPER_MAP.put("10X14", 16);
        PAPER_MAP.put("11X17", 17);
        PAPER_MAP.put("NOTE", 18);
        PAPER_MAP.put("ENV9", 19);
        PAPER_MAP.put("ENV10", 20);
        PAPER_MAP.put("ENV11", 21);
        PAPER_MAP.put("ENV12", 22);
        PAPER_MAP.put("ENV14", 23);
        PAPER_MAP.put("CSHEET", 24);
        PAPER_MAP.put("DSHEET", 25);
        PAPER_MAP.put("ESHEET", 26);
        PAPER_MAP.put("ENVDL", 27);
        PAPER_MAP.put("ENVC5", 28);
        PAPER_MAP.put("ENVC3", 29);
        PAPER_MAP.put("ENVC4", 30);
        PAPER_MAP.put("ENVC6", 31);
        PAPER_MAP.put("ENVC65", 32);
        PAPER_MAP.put("ENVB4", 33);
        PAPER_MAP.put("ENVB5", 34);
        PAPER_MAP.put("ENVB6", 35);
        PAPER_MAP.put("ENVITALY", 36);
        PAPER_MAP.put("ENVMONARCH", 37);
        PAPER_MAP.put("ENVPERSONAL", 38);
        PAPER_MAP.put("FANFOLDUS", 39);
        PAPER_MAP.put("FANFOLDSTDGERMAN", 40);
        PAPER_MAP.put("FANFOLDLGLGERMAN", 41);
        PAPER_MAP.put("ISOB4", 42);
        PAPER_MAP.put("JAPANESEPOSTCARD", 43);
        PAPER_MAP.put("9X11", 44);
        PAPER_MAP.put("10X11", 45);
        PAPER_MAP.put("15X11", 46);
        PAPER_MAP.put("ENVINVITE", 47);
        PAPER_MAP.put("LETTEREXTRA", 50);
        PAPER_MAP.put("LEGALEXTRA", 51);
        PAPER_MAP.put("TABLOIDEXTRA", 52);
        PAPER_MAP.put("A4EXTRA", 53);
        PAPER_MAP.put("LETTERTRANSVERSE", 54);
        PAPER_MAP.put("A4TRANSVERSE", 55);
        PAPER_MAP.put("LETTEREXTRATRANSVERSE", 56);
        PAPER_MAP.put("APLUS", 57);
        PAPER_MAP.put("BPLUS", 58);
        PAPER_MAP.put("LETTERPLUS", 59);
        PAPER_MAP.put("A4PLUS", 60);
        PAPER_MAP.put("A5TRANSVERSE", 61);
        PAPER_MAP.put("B5TRANSVERSE", 62);
        PAPER_MAP.put("A3EXTRA", 63);
        PAPER_MAP.put("A5EXTRA", 64);
        PAPER_MAP.put("B5EXTRA", 65);
        PAPER_MAP.put("A2", 66);
        PAPER_MAP.put("A3TRANSVERSE", 67);
        PAPER_MAP.put("A3EXTRATRANSVERSE", 68);
        PAPER_MAP.put("DBLJAPANESEPOSTCARD", 69);
        PAPER_MAP.put("A6", 70);
        PAPER_MAP.put("ENVKAKU2", 71);
        PAPER_MAP.put("ENVKAKU3", 72);
        PAPER_MAP.put("ENVCHOU3", 73);
        PAPER_MAP.put("ENVCHOU4", 74);
        PAPER_MAP.put("LETTERROTATED", 75);
        PAPER_MAP.put("A3ROTATED", 76);
        PAPER_MAP.put("A4ROTATED", 77);
        PAPER_MAP.put("A5ROTATED", 78);
        PAPER_MAP.put("B4JISROTATED", 79);
        PAPER_MAP.put("B5JISROTATED", 80);
        PAPER_MAP.put("JAPANESEPOSTCARDROTATED", 81);
        PAPER_MAP.put("DBLJAPANESEPOSTCARDROTATED", 82);
        PAPER_MAP.put("A6ROTATED", 83);
        PAPER_MAP.put("ENVKAKU2ROTATED", 84);
        PAPER_MAP.put("ENVKAKU3ROTATED", 85);
        PAPER_MAP.put("ENVCHOU3ROTATED", 86);
        PAPER_MAP.put("ENVCHOU4ROTATED", 87);
        PAPER_MAP.put("B6JIS", 88);
        PAPER_MAP.put("B6JISROTATED", 89);
        PAPER_MAP.put("12X11", 90);
        PAPER_MAP.put("ENVYOU4", 91);
        PAPER_MAP.put("ENVYOU4ROTATED", 92);
        PAPER_MAP.put("PRC16K", 93);
        PAPER_MAP.put("PRC32K", 94);
        PAPER_MAP.put("PRC32KBIG", 95);
        PAPER_MAP.put("ENVPRC1", 96);
        PAPER_MAP.put("ENVPRC2", 97);
        PAPER_MAP.put("ENVPRC3", 98);
        PAPER_MAP.put("ENVPRC4", 99);
        PAPER_MAP.put("ENVPRC5", 100);
        PAPER_MAP.put("ENVPRC6", 101);
        PAPER_MAP.put("ENVPRC7", 102);
        PAPER_MAP.put("ENVPRC8", 103);
        PAPER_MAP.put("ENVPRC9", 104);
        PAPER_MAP.put("ENVPRC10", 105);
        PAPER_MAP.put("PRC16KROTATED", 106);
        PAPER_MAP.put("PRC32KROTATED", 107);
        PAPER_MAP.put("PRC32KBIGROTATED", 108);
        PAPER_MAP.put("ENVPRC1ROTATED", 109);
        PAPER_MAP.put("ENVPRC2ROTATED", 110);
        PAPER_MAP.put("ENVPRC3ROTATED", 111);
        PAPER_MAP.put("ENVPRC4ROTATED", 112);
        PAPER_MAP.put("ENVPRC5ROTATED", 113);
        PAPER_MAP.put("ENVPRC6ROTATED", 114);
        PAPER_MAP.put("ENVPRC7ROTATED", 115);
        PAPER_MAP.put("ENVPRC8ROTATED", 116);
        PAPER_MAP.put("ENVPRC9ROTATED", 117);
        PAPER_MAP.put("ENVPRC10ROTATED", 118);

        // ================================================================
        // 扩展 119~144 (Microsoft Print to PDF)
        // ================================================================
        PAPER_MAP.put("ASMEFSHEET", 119);
        PAPER_MAP.put("ARCHITECTUREASHEET", 120);
        PAPER_MAP.put("ARCHITECTUREBSHEET", 121);
        PAPER_MAP.put("ARCHITECTURECSHEET", 122);
        PAPER_MAP.put("ARCHITECTUREDSHEET", 123);
        PAPER_MAP.put("ARCHITECTUREE1SHEET", 124);
        PAPER_MAP.put("ARCHITECTUREESHEET", 125);
        PAPER_MAP.put("BUSINESSCARD2X35", 126);
        PAPER_MAP.put("BUSINESSCARD55X85MM", 127);
        PAPER_MAP.put("BUSINESSCARD55X91MM", 128);
        PAPER_MAP.put("14X17", 129);
        PAPER_MAP.put("PHOTOL", 130);
        PAPER_MAP.put("JAPANCHOU40ENVELOPE", 131);
        PAPER_MAP.put("METRICPHOTOL", 132);
        PAPER_MAP.put("3X5", 133);
        PAPER_MAP.put("5X8", 134);
        PAPER_MAP.put("10X12", 135);
        PAPER_MAP.put("4X4", 136);
        PAPER_MAP.put("5X5", 137);
        PAPER_MAP.put("PHOTO89X89MM", 138);
        PAPER_MAP.put("CREDITCARD", 139);
        PAPER_MAP.put("ISOA0", 140);
        PAPER_MAP.put("ISOA1", 141);
        PAPER_MAP.put("4X6", 142);
        PAPER_MAP.put("5X7", 143);
        PAPER_MAP.put("8X10", 144);

        // ================================================================
        // 扩展 145~177 (Adobe PDF)
        // ================================================================
        PAPER_MAP.put("ADOBELETTER", 145);
        PAPER_MAP.put("ADOBELEGAL", 146);
        PAPER_MAP.put("ADOBE11X17", 147);
        PAPER_MAP.put("SCREEN", 148);
        PAPER_MAP.put("ANSIC", 149);
        PAPER_MAP.put("ANSID", 150);
        PAPER_MAP.put("ANSIE", 151);
        PAPER_MAP.put("ANSIF", 152);
        PAPER_MAP.put("ARCHA", 153);
        PAPER_MAP.put("ARCHB", 154);
        PAPER_MAP.put("ARCHC", 155);
        PAPER_MAP.put("ARCHD", 156);
        PAPER_MAP.put("ARCHE", 157);
        PAPER_MAP.put("ARCHE1", 158);
        PAPER_MAP.put("ARCHE2", 159);
        PAPER_MAP.put("ARCHE3", 160);
        PAPER_MAP.put("A1", 161);
        PAPER_MAP.put("A0", 162);
        PAPER_MAP.put("OVERSIZEA2", 163);
        PAPER_MAP.put("OVERSIZEA1", 164);
        PAPER_MAP.put("OVERSIZEA0", 165);
        PAPER_MAP.put("ISOB5", 166);
        PAPER_MAP.put("ISOB4ADOBE", 167);
        PAPER_MAP.put("ISOB2", 168);
        PAPER_MAP.put("ISOB1", 169);
        PAPER_MAP.put("ADOBEC5", 170);
        PAPER_MAP.put("ADOBEJISB4", 171);
        PAPER_MAP.put("JISB3", 172);
        PAPER_MAP.put("JISB2", 173);
        PAPER_MAP.put("JISB1", 174);
        PAPER_MAP.put("JISB0", 175);
        PAPER_MAP.put("92X92", 176);
        PAPER_MAP.put("SLIDE75X10", 177);

        // PostScript 自定义
        PAPER_MAP.put("PSCUSTOM", 32767);

        // ================================================================
        // 标准别名
        // ================================================================
        PAPER_MAP.put("SUPERA", 57);
        PAPER_MAP.put("SUPERB", 58);
        PAPER_MAP.put("JISB4", 12);
        PAPER_MAP.put("JISB5", 13);
        PAPER_MAP.put("JISB6", 88);
        PAPER_MAP.put("ANSIA", 1);
        PAPER_MAP.put("ANSIB", 3);
        PAPER_MAP.put("DL", 27);
        PAPER_MAP.put("C3", 29);
        PAPER_MAP.put("C4", 30);
        PAPER_MAP.put("C5", 28);
        PAPER_MAP.put("C6", 31);
        PAPER_MAP.put("C65", 32);
        PAPER_MAP.put("MONARCH", 37);
    }

    /**
     * 获取纸张编号 (Kind)
     *
     * @param rawName CUPS返回的原始名称 (如 "*A4")
     * @return 对应的DMPAPER ID，匹配不到返回 256 (Custom)
     */
    public static int getKind(String rawName) {
        if (StringUtils.isEmpty(rawName)) return 256;

        // 1. 处理前缀星号和空格
        String key = rawName.replace("*", "").trim().toUpperCase();

        // 2. 查找映射
        return PAPER_MAP.getOrDefault(key, 256);
    }
}
