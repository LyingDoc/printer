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
     * 获取任务列表
     *
     * @param http       http连接，传 null 表示默认本地连接
     * @param jobs       任务数组的指针引用
     * @param name       打印机名称，传 null 表示所有打印机
     * @param my_jobs    是否只看自己的任务 (1=是, 0=所有用户)
     * @param which_jobs 哪些任务 (-1=活跃任务, 0=所有任务, 1=已完成任务)
     * @return 任务数量
     */
    int cupsGetJobs2(Pointer http, PointerByReference jobs, String name, int my_jobs, int which_jobs);

    /**
     * 释放任务列表内存 (极其重要，否则会 OOM)
     */
    void cupsFreeJobs(int num_jobs, Pointer jobs);

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
