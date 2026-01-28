package com.tk.lyin.platform;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

public interface LibCpus extends Library {
    LibCpus instance = Native.load("cups", LibCpus.class);

    /**
     * 获取所有打印机目标
     *
     * @param dests 用于接收指向结构体数组指针的引用
     * @return 返回获取到的打印机总数
     */
    int cupsGetDests(PointerByReference dests);

    /**
     * 释放由 cupsGetDests 分配的内存
     */
    void cupsFreeDests(int num_dests, Pointer dests);

    /**
     * 获取打印机的 PPD 文件路径
     *
     * @return 返回临时文件的绝对路径，使用完后必须手动删除
     */
    String cupsGetPPD(String printer);

    /**
     * 获取选项值
     */
    String cupsGetOption(String name, int num_options, Pointer options);

    /**
     * CUPS 打印机目标结构体
     */
    @Structure.FieldOrder({"name", "instance", "is_default", "num_options", "options"})
    class CupsDest extends Structure {
        public String name;        // 打印机名称 (对应 PrinterName)
        public String instance;    // 实例名 (通常为 null)
        public int is_default;     // 1 代表是默认打印机
        public int num_options;    // 选项数量
        public Pointer options;    // 详细选项指针

        public CupsDest(Pointer pointer) {
            super(pointer);
            read();
        }

        public static class ByReference extends CupsDest implements Structure.ByReference {
            public ByReference(Pointer pointer) {
                super(pointer);
            }
        }
    }
}
