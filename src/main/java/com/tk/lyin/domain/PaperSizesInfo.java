package com.tk.lyin.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaperSizesInfo {
    public String printerName;
    public int taskNumber;
    public int status;
    public String statusMsg;
    public List<PaperSize> paperSizes;

    @Getter
    @Setter
    public static class PaperSize {
        public int height;
        public int kind;
        public String paperName;
        public int rawKind;
        public String rawPaperName;
        public int width;
    }
}
