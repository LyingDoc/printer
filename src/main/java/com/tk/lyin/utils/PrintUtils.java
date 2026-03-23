package com.tk.lyin.utils;

import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocument;
import org.dromara.pdf.shade.org.apache.pdfbox.printing.PDFPrintable;
import org.dromara.pdf.shade.org.apache.pdfbox.printing.Scaling;

import javax.print.attribute.standard.*;
import java.awt.print.PrinterJob;
import java.lang.reflect.Field;

public class PrintUtils {

    /*打印页数子集*/
    public static final String ODD = "odd";// 偶数
    public static final String EVEN = "even";// 奇数

    public static MediaSizeName parseMediaSize(String paperSizeStr) {
        if (StringUtils.isEmpty(paperSizeStr)) {
            return MediaSizeName.ISO_A4; // 默认值
        }
        // 核心逻辑：匹配 Java 标准库中的属性
        // Java 的属性名通常是 ISO_A3，而传过来的是 iso-a3
        String targetName = paperSizeStr.replace("-", "_").toUpperCase();
        try {
            // 利用反射直接从 MediaSizeName 类中获取静态常量
            Field field = MediaSizeName.class.getDeclaredField(targetName);
            return (MediaSizeName) field.get(null);
        } catch (Exception e) {
            System.err.println("未找到匹配的纸张类型: " + paperSizeStr + "，将使用默认 A4");
            return MediaSizeName.ISO_A4;
        }
    }

    public static OrientationRequested parseOrientation(String orientationStr) {
        if (StringUtils.isEmpty(orientationStr)) {
            return OrientationRequested.PORTRAIT; // 默认值
        }

        // 核心逻辑映射：
        // 1. 将小驼峰 "reverseLandscape" 转为 "REVERSE_LANDSCAPE"
        // 2. 将横杠 "reverse-landscape" 转为 "REVERSE_LANDSCAPE"
        String targetName = orientationStr
                .replaceAll("([a-z])([A-Z])", "$1_$2") // 处理驼峰：reverseLandscape -> reverse_Landscape
                .replace("-", "_")                      // 处理横杠：reverse-landscape -> reverse_landscape
                .toUpperCase();                         // 全大写：REVERSE_LANDSCAPE
        try {
            // 利用反射从 OrientationRequested 类中获取静态常量
            Field field = OrientationRequested.class.getDeclaredField(targetName);
            return (OrientationRequested) field.get(null);
        } catch (Exception e) {
            System.err.println("未找到匹配的方向类型: " + orientationStr + "，将使用默认 PORTRAIT");
            return OrientationRequested.PORTRAIT;
        }
    }

    public static Chromaticity parseChromaticity(String chromaticityStr) {
        if (StringUtils.isEmpty(chromaticityStr)) {
            return Chromaticity.MONOCHROME; // 默认使用黑色，符合现代大多数场景
        }

        // 转换逻辑：直接转大写。
        // "monochrome" -> "MONOCHROME"
        // "color" -> "COLOR"
        String targetName = chromaticityStr.toUpperCase();

        try {
            // 利用反射从 Chromaticity 类中获取静态常量
            Field field = Chromaticity.class.getDeclaredField(targetName);
            return (Chromaticity) field.get(null);
        } catch (Exception e) {
            System.err.println("未找到匹配的颜色模式: " + chromaticityStr + "，将使用默认 COLOR");
            return Chromaticity.MONOCHROME;
        }
    }

    public static Sides parseSides(String sidesStr) {
        if (StringUtils.isEmpty(sidesStr)) {
            return Sides.ONE_SIDED; // 默认单面
        }

        // 转换逻辑：将 "one-sided" 转为 "ONE_SIDED"
        String targetName = sidesStr.replace("-", "_").toUpperCase();

        try {
            Field field = Sides.class.getDeclaredField(targetName);
            return (Sides) field.get(null);
        } catch (Exception e) {
            System.err.println("未找到匹配的双面模式: " + sidesStr + "，将使用默认 ONE_SIDED");
            return Sides.ONE_SIDED;
        }
    }

    public static MediaTray parseMediaTray(String trayStr) {
        if (StringUtils.isEmpty(trayStr)) {
            return MediaTray.MAIN; // 默认主纸盒
        }

        // 转换逻辑：将 "large-capacity" 转为 "LARGE_CAPACITY"
        String targetName = trayStr.replace("-", "_").toUpperCase();

        try {
            Field field = MediaTray.class.getDeclaredField(targetName);
            return (MediaTray) field.get(null);
        } catch (Exception e) {
            // MediaTray 比较特殊，如果反射找不到，尝试遍历标准纸盒
            System.err.println("反射未找到纸盒: " + trayStr + "，尝试标准匹配...");
            return MediaTray.MAIN;
        }
    }

    public static void setScaling(PDDocument pdDocument, PrinterJob job, String scaling) {
        Scaling scale = Scaling.SCALE_TO_FIT;
        if (StringUtils.isNotEmpty(scaling)) {
            // 设置缩放
            switch (scaling) {
                case "actual-size":
                    scale = Scaling.ACTUAL_SIZE;
                    break;
                case "shrink-to-fits":
                    scale = Scaling.SHRINK_TO_FIT;
                    break;
                case "stretch-to-fits":
                    scale = Scaling.STRETCH_TO_FIT;
                    break;
            }
        }
        PDFPrintable pdfPrintable = new PDFPrintable(pdDocument, scale);
        job.setPrintable(pdfPrintable);
    }

    public static void setSubSet(PDDocument pdDocument, String subSet) {
        if (StringUtils.isEmpty(subSet)) {
            return;
        }
        int totalPages = pdDocument.getNumberOfPages();
        // 2. 从最后一页开始往前循环，防止索引崩溃
        for (int i = totalPages - 1; i >= 0; i--) {
            int pageNumber = i + 1; // 转换为物理页码 (1, 2, 3...)

            if (StringUtils.equals(subSet, ODD)) {
                // 如果只要奇数页：移除所有偶数页
                if (pageNumber % 2 == 0) {
                    pdDocument.removePage(i);
                }
            } else if (StringUtils.equals(subSet, EVEN)) {
                // 如果只要偶数页：移除所有奇数页
                if (pageNumber % 2 != 0) {
                    pdDocument.removePage(i);
                }
            }
        }
    }
}
