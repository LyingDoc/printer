package com.tk.lyin.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.Cups;
import com.sun.jna.platform.win32.Winspool;
import com.sun.jna.platform.win32.WinspoolUtil;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.tk.lyin.domain.*;
import com.tk.lyin.platform.LibCpus;
import com.tk.lyin.platform.WinSpool;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PrintServiceUtils {

    private static final short DC_PAPERS = 2;
    private static final short DC_PAPERSIZE = 3;
    private static final short DC_PAPERNAMES = 16;
    private static final Pattern DIM_PATTERN = Pattern.compile("^\\*PaperDimension\\s+([^/\\s:]+).*?:\\s*\"([\\d.]+)\\s+([\\d.]+)\"");


    public static Printer getDefaultPrinter() {
        Printer printer = new Printer();
        if (OSUtils.IS_OS_UNIX) {
            String printerDefaultName = Cups.INSTANCE.cupsGetDefault();
            printer.setPrinterName(printerDefaultName);
            return printer;
        }

        //获取所需的缓冲区长度
        IntByReference intByReference = new IntByReference();
        WinSpool.instance.GetDefaultPrinter(null, intByReference);
        int intByReferenceValue = intByReference.getValue();
        if (intByReferenceValue > 0) {
            char[] pszBuffer = new char[intByReferenceValue];
            if (WinSpool.instance.GetDefaultPrinter(pszBuffer, intByReference)) {
                String name = StringUtils.charToStr(pszBuffer);
                printer.setPrinterName(name);
            }
        }
        return printer;
    }

    public static List<Printer> getPrinters() {
        List<Printer> result = new ArrayList<>();
        if (OSUtils.IS_OS_UNIX) {
            result = CpusUtil.processPrinters(cupsDests -> {
                List<Printer> printers = new ArrayList<>();
                for (Cups.CupsDest cupsDest : cupsDests) {
                    Printer printer = new Printer();
                    printer.setPrinterName(cupsDest.name);
                    printers.add(printer);
                }
                return printers;
            });
            return result;
        }

        Winspool.PRINTER_INFO_4[] printerInfos = WinspoolUtil.getPrinterInfo4();
        for (Winspool.PRINTER_INFO_4 printerInfo : printerInfos) {
            Printer printer = new Printer();
            printer.setPrinterName(printerInfo.pPrinterName);
            result.add(printer);
        }

        return result;
    }


    @SneakyThrows
    public static PaperSizesInfo getPaperSizeInfo(String printerName) {
        // 初始化返回对象
        PaperSizesInfo result = new PaperSizesInfo();
        // 空字符则获取默认打印机”的逻辑
        final String finalPrinterName = StringUtils.isEmpty(printerName)
                ? getDefaultPrinter().getPrinterName()
                : printerName;
        if (finalPrinterName == null) return null;

        if (OSUtils.IS_OS_UNIX) {
            // 1. 增强型正则，适配更多驱动格式
            result = CpusUtil.processPrinters(cupsDests -> {
                PaperSizesInfo paperSizesInfo = new PaperSizesInfo();
                for (Cups.CupsDest cupsDest : cupsDests) {

                    int numOptions = cupsDest.num_options;
                    Pointer options = cupsDest.options;
                    String pName = cupsDest.name;

                    if (StringUtils.equals(pName, finalPrinterName)) {
                        // 填充状态信息
                        getPrinterInfo(paperSizesInfo, pName, numOptions, options);
                    }
                }

                return paperSizesInfo;
            });
            return result;
        }

        Winspool.PRINTER_INFO_2 printerInfo = WinspoolUtil.getPrinterInfo2(finalPrinterName);

        result.setPrinterName(printerInfo.pPrinterName);
        result.setTaskNumber(printerInfo.cJobs);
        result.setStatus(printerInfo.Status);
        // 按照你的类定义，StatusMsg 目前设为 Status 原始值，稍后可按位解析
        result.setStatusMsg(PrinterStatus.parseDescription(printerInfo.Status));
        // 调用底层 API 获取该打印机的所有纸张详情
        result.setPaperSizes(fetchPaperSizes(printerInfo.pPrinterName));
        return result;
    }


    @SneakyThrows
    public static List<PaperSizesInfo> getPaperSizeInfoAll() {
        List<PaperSizesInfo> result = new ArrayList<>();

        if (OSUtils.IS_OS_UNIX) {
            result = CpusUtil.processPrinters(cupsDests -> {

                List<PaperSizesInfo> paperSizesInfos = new ArrayList<>();
                for (Cups.CupsDest cupsDest : cupsDests) {
                    PaperSizesInfo paperSizesInfo = new PaperSizesInfo();

                    int numOptions = cupsDest.num_options;
                    Pointer options = cupsDest.options;
                    String pName = cupsDest.name;
                    // 填充状态信息
                    getPrinterInfo(paperSizesInfo, pName, numOptions, options);
                    paperSizesInfos.add(paperSizesInfo);
                }

                return paperSizesInfos;
            });
            return result;
        }
        Winspool.PRINTER_INFO_2[] printerInfos = WinspoolUtil.getAllPrinterInfo2();
        for (Winspool.PRINTER_INFO_2 printerInfo : printerInfos) {
            PaperSizesInfo info = new PaperSizesInfo();
            info.setPrinterName(printerInfo.pPrinterName);
            info.setTaskNumber(printerInfo.cJobs);
            info.setStatus(printerInfo.Status);
            // 按照你的类定义，StatusMsg 目前设为 Status 原始值，稍后可按位解析
            info.setStatusMsg(PrinterStatus.parseDescription(printerInfo.Status));
            // 调用底层 API 获取该打印机的所有纸张详情
            info.setPaperSizes(fetchPaperSizes(printerInfo.pPrinterName));
            result.add(info);
        }
        return result;
    }


    @SneakyThrows
    public static void print(PrintOptions printOptions) {
        String printerName = printOptions.getPrinter();
        String filePath = printOptions.getPdf();
        if (StringUtils.isEmpty(filePath)) {
            System.err.println("printer-file path is empty");
            return;
        }
        if (StringUtils.isEmpty(printerName)) {
            printerName = getDefaultPrinter().getPrinterName();
        }
        // 判断Linux环境中没有打印机情况
        if (StringUtils.isEmpty(printerName)) {
            System.err.println("printer-name is empty");
            return;
        }
        PDDocument pdDocument = PdfHandler.getDocumentHandler().load(filePath).getTarget();

        PrinterJob job = PrinterJob.getPrinterJob();
        // 查找指定的打印机
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            if (StringUtils.equals(service.getName(), printerName)) {
                job.setPrintService(service);
                break;
            }
        }
        // 设置任务名称
        job.setJobName(filePath);

        // 设置属性
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        //设置打印纸张大小
        attributes.add(PrintUtils.parseMediaSize(printOptions.getPaperSize()));
        // 是设置打印份数
        Integer copies = printOptions.getCopies();
        if (copies != null) {
            job.setCopies(copies);
        }
        // 设置彩印
        attributes.add(PrintUtils.parseChromaticity(printOptions.getMonochrome()));
        // 设置打印机方向
        attributes.add(PrintUtils.parseOrientation(printOptions.getOrientation()));
        // 墨盒类型
        attributes.add(PrintUtils.parseMediaTray(printOptions.getBin()));
        // 设置单双面
        attributes.add(PrintUtils.parseSides(printOptions.getSide()));
        // 设置打印范围
        String pages = printOptions.getPages();
        if (StringUtils.isNotEmpty(pages)) {
            attributes.add(new PageRanges(pages));
        }
        // 设置奇、偶打印
        PrintUtils.setSubSet(pdDocument, printOptions.getSubset());
        //设置缩放，关键：将 PDF 转换为可打印的 Pageable 对象
        PrintUtils.setScaling(pdDocument, job, printOptions.getScale());
        // 设置对话框打印
        if (printOptions.getPrintDialog()) {
            job.printDialog(attributes);
            return;
        }
        job.print(attributes);
    }


    @SneakyThrows
    private static void getPrinterInfo(PaperSizesInfo paperSizesInfo, String pName, int numOptions, Pointer options) {

        String ppdPath = LibCpus.instance.cupsGetPPD(pName);
        if (StringUtils.isEmpty(ppdPath)) {
            System.err.println("ppd-path is empty");
            return;
        }
        try {
            List<PaperSizesInfo.PaperSize> paperSizes = getPaperSizeList(ppdPath);
            paperSizesInfo.setPaperSizes(paperSizes);
        } finally {
            FileUtils.delete(new File(ppdPath)); // 养成清理 PPD 临时文件的习惯
        }


        // 获取打印机状态信息
        PointerByReference jobsRef = new PointerByReference();
        int numJobs = Cups.INSTANCE.cupsGetJobs2(null, jobsRef, pName, 0,
                Cups.CUPS_WHICHJOBS_ALL);
        if (numJobs < 0) {
            System.err.println("CUPS scheduler not running, skipping job test");
            return;
        }
        Pointer jobsPtr = jobsRef.getValue();
        Cups.CupsJob job = new Cups.CupsJob(jobsPtr);

        paperSizesInfo.setPrinterName(job.dest);

        // 获取任务状态码以及任务原因
        String stateStr = Cups.INSTANCE.cupsGetOption("printer-state", numOptions, options);
        int state = (stateStr != null) ? Integer.parseInt(stateStr) : 0;
        paperSizesInfo.setStatus(state);
        String reasons = Cups.INSTANCE.cupsGetOption("printer-state-reasons", numOptions, options);
        paperSizesInfo.setStatusMsg(LibCpusStatus.parseDescription(reasons));

        // --- 优化任务数获取逻辑 ---
        paperSizesInfo.setTaskNumber(numJobs);
        // 关键：释放 C 申请的内存
        Cups.INSTANCE.cupsFreeJobs(numJobs, jobsPtr);
    }

    @SneakyThrows
    private static List<PaperSizesInfo.PaperSize> getPaperSizeList(String ppdPath) {
        List<PaperSizesInfo.PaperSize> paperSizes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(ppdPath));
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = DIM_PATTERN.matcher(line);
            if (matcher.find()) {
                PaperSizesInfo.PaperSize ps = getPaperSize(matcher);
                paperSizes.add(ps);
            }
        }
        return paperSizes;
    }

    private static PaperSizesInfo.PaperSize getPaperSize(Matcher matcher) {
        String name = matcher.group(1).trim();
        float pw = Float.parseFloat(matcher.group(2));
        float ph = Float.parseFloat(matcher.group(3));
        int currentRawKind = PaperKind.getKind(name);

        PaperSizesInfo.PaperSize ps = new PaperSizesInfo.PaperSize();
        ps.setPaperName(name);
        ps.setWidth(Math.round(pw * 3.52778f));
        ps.setHeight(Math.round(ph * 3.52778f));

        // 3. 修正赋值：Kind 使用名称映射，RawKind 使用哈希
        ps.setKind(currentRawKind); // 需实现名称转 ID 逻辑
        ps.setRawKind(currentRawKind);
        return ps;
    }


    /**
     * 调用 DeviceCapabilities 获取底层纸张信息
     */
    private static List<PaperSizesInfo.PaperSize> fetchPaperSizes(String printerName) {
        List<PaperSizesInfo.PaperSize> paperSizes = new ArrayList<>();
        // 1. 获取纸张数量
        int count = WinSpool.instance.DeviceCapabilities(printerName, null, DC_PAPERS, null, null);
        if (count <= 0) return paperSizes;

        // 2. 准备缓冲区
        byte[] rawKindsBuf = new byte[count * 2];   // short: 2 bytes
        byte[] namesBuf = new byte[count * 64 * 2]; // Unicode Name: 64 chars (128 bytes)
        byte[] sizesBuf = new byte[count * 8];      // Point: 2 longs (4 width + 4 height, 8 bytes)

        // 3. 填充数据
        WinSpool.instance.DeviceCapabilities(printerName, null, DC_PAPERS, rawKindsBuf, null);
        WinSpool.instance.DeviceCapabilities(printerName, null, DC_PAPERNAMES, namesBuf, null);
        WinSpool.instance.DeviceCapabilities(printerName, null, DC_PAPERSIZE, sizesBuf, null);

        for (int i = 0; i < count; i++) {
            PaperSizesInfo.PaperSize ps = new PaperSizesInfo.PaperSize();

            // 解析 RawKind (2字节 short)
            int rawKind = (rawKindsBuf[i * 2] & 0xFF) | ((rawKindsBuf[i * 2 + 1] & 0xFF) << 8);
            ps.setRawKind(rawKind);

            // 解析 PaperName (Windows UNICODE 每个名字固定 64 字符长度块)
            String name = new String(namesBuf, i * 128, 128, StandardCharsets.UTF_16LE).trim();
            ps.setPaperName(name);

            // 解析 Width 和 Height (4字节 Long, 单位 0.1mm)
            ps.setWidth(readInt(sizesBuf, i * 8));
            ps.setHeight(readInt(sizesBuf, i * 8 + 4));

            // Kind 设置为默认或与 RawKind 同步
            ps.setKind(rawKind);
            paperSizes.add(ps);
        }


        return paperSizes;
    }

    /**
     * 小端序读取 4 字节整数
     */
    private static int readInt(byte[] buf, int offset) {
        return (buf[offset] & 0xFF) |
                ((buf[offset + 1] & 0xFF) << 8) |
                ((buf[offset + 2] & 0xFF) << 16) |
                ((buf[offset + 3] & 0xFF) << 24);
    }

}
