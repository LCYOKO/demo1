package com.xiaomi.demo.document.report.word.poi;

/**
 * @Author:柳维民
 * @Date: 2023/9/26 14:56
 */
public enum SourceFromTypeEnum {

    /**
     * 从docx表格中获取
     */
    SOURCE_FROM_TABLE(1, "XWPFTable"),

    /**
     * 从段落中获取
     */
    SOURCE_FROM_PARAGRAPH(2, "XWPFParagraph");

    int code;
    String name;


    SourceFromTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
