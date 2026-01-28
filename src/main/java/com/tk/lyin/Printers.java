package com.tk.lyin;

import com.alibaba.fastjson2.JSON;
import com.tk.lyin.domain.PaperSizesInfo;
import com.tk.lyin.domain.PrintOptions;
import com.tk.lyin.domain.Printer;
import com.tk.lyin.utils.PrintServiceUtils;
import com.tk.lyin.utils.StringUtils;

import java.util.Base64;
import java.util.List;


public class Printers {
    public static void main(String[] args) {
        String arg = args[0];
        String printerName = "";
        int length = args.length;
        if (StringUtils.equals(arg, Printer.DEFAULT)) {
            Printer printer = PrintServiceUtils.getDefaultPrinter();
            System.out.println(JSON.toJSONString(printer));
            return;

        }
        if (StringUtils.equals(arg, Printer.PRINTERS)) {
            List<Printer> printers = PrintServiceUtils.getPrinters();
            System.out.println(JSON.toJSONString(printers));
            return;

        }
        if (StringUtils.equals(arg, Printer.SIZEINFO)) {
            if (length >= 2) {
                printerName = args[1];
            }
            PaperSizesInfo paperSizesInfo = PrintServiceUtils.getPaperSizeInfo(printerName);
            System.out.println(JSON.toJSONString(paperSizesInfo));
            return;
        }
        if (StringUtils.equals(arg, Printer.SIZEINFOS)) {
            List<PaperSizesInfo> paperSizesInfos = PrintServiceUtils.getPaperSizeInfoAll();
            System.out.println(JSON.toJSONString(paperSizesInfos));
            return;

        }
        if (StringUtils.equals(arg, Printer.PRINT)) {
            String printers = args[1];
            String jsonStr = new String(Base64.getDecoder().decode(printers));
            PrintOptions printOptions = JSON.parseObject(jsonStr, PrintOptions.class);
            PrintServiceUtils.print(printOptions);
        }
    }


}



