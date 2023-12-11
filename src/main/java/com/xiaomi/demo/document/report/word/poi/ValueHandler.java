package com.xiaomi.demo.document.report.word.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @Author:柳维民
 * @Date: 2023/9/26 15:37
 */
public interface ValueHandler {

    String getTargetValueFromSource(DocxPosition docxPosition, XWPFDocument document);
}
