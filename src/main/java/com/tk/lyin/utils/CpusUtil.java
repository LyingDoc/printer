package com.tk.lyin.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.Cups;
import com.sun.jna.ptr.PointerByReference;
import lombok.SneakyThrows;

import java.io.IOException;

public class CpusUtil {

    // 将方法改为接收一个 Processor
    @SneakyThrows
    public static <T> T processPrinters(PrinterProcessor<T> processor) {
        PointerByReference pointerByReference = new PointerByReference();
        // 1. 申请内存
        int numDests = Cups.INSTANCE.cupsGetDests(pointerByReference);
        Pointer pointer = pointerByReference.getValue();

        T result = null;
        try {
            if (numDests > 0 && pointer != null) {
                // 2. 转换为 Java 结构体供 Java 代码使用
                Cups.CupsDest cupsDest = new Cups.CupsDest(pointer);
                // 注意：这里 toArray 需要你的 JNA Structure 类正确配置了
                Cups.CupsDest[] dests = (Cups.CupsDest[]) cupsDest.toArray(numDests);
                // 3. 执行外部传入的逻辑
                result = processor.process(dests);
            }
        } finally {
            // 4. 确保释放内存！无论中间发生什么
            if (numDests > 0 && pointer != null) {
                Cups.INSTANCE.cupsFreeDests(numDests, pointer);
            }
        }
        return result;
    }

    // 定义一个接口，用来处理找到的打印机列表
    public interface PrinterProcessor<T> {
        T process(Cups.CupsDest[] dests) throws IOException;
    }
}
