package com.tk.lyin.platform;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public interface WinSpool extends Library {
    WinSpool instance = Native.load("winspool.drv", WinSpool.class, W32APIOptions.UNICODE_OPTIONS);

    /**
     * 获取系统默认打印机名称
     *
     * @param pszBuffer  接收名称的缓冲区
     * @param pcchBuffer 缓冲区大小（字符数）
     * @return 是否成功
     */
    boolean GetDefaultPrinter(char[] pszBuffer, IntByReference pcchBuffer);

    /**
     * 查询打印机设备能力
     * * @param pDevice      打印机名称（例如 "Microsoft Print to PDF"）。不能为空字符串。
     *
     * @param pPort        端口名称（例如 "LPT1:"）。在现代 Windows 系统中通常传 null。
     * @param fwCapability 要查询的能力常量。常见值：
     *                     DC_PAPERS (2): 获取支持的纸张 RawKind 列表。
     *                     DC_PAPERNAMES (16): 获取支持的纸张名称列表。
     *                     DC_PAPERSIZE (3): 获取纸张尺寸（单位 0.1mm）。
     *                     DC_BINS (6): 获取进纸盒列表。
     * @param pOutput      输出缓冲区。
     *                     - 若为 null，函数通常返回所需的数据条目数（count）。
     *                     - 若不为 null，函数将查询到的数据写入此字节数组。
     * @param pDevMode     指向 DEVMODE 结构的指针。通常传 null，表示使用打印机驱动的默认设置。
     *                     * @return 如果 pOutput 为 null，返回所需条目数量；
     *                     如果 pOutput 不为 null，返回执行成功后的条目数；
     *                     返回 -1 表示执行失败或该功能不受支持。
     */
    int DeviceCapabilities(String pDevice, String pPort, short fwCapability, byte[] pOutput, byte[] pDevMode);
}
