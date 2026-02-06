package com.tk.lyin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrintOptions {
    private String printer;
    private String pdf;
    private String pages;
    private String subset;
    private String orientation;
    private String scale;
    private String monochrome;
    private String side;
    private String bin;
    private String paperSize;
    private Boolean printDialog;
    private Integer copies;
}
