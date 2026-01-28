package com.tk.lyin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Printer {
    public static final String DEFAULT = "default";
    public static final String PRINTERS = "printers";
    public static final String SIZEINFO = "sizeInfo";
    public static final String SIZEINFOS = "sizeInfos";
    public static final String PRINT = "print";


    private String deviceID;
    private String printerName;
}
