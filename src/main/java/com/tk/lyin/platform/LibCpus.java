package com.tk.lyin.platform;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LibCpus extends Library {
    LibCpus instance = Native.load("cups", LibCpus.class);

    /**
     * 获取打印机的 PPD 文件路径
     *
     * @return 返回临时文件的绝对路径，使用完后必须手动删除
     */
    String cupsGetPPD(String printer);

}
