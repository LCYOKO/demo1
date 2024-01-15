package com.xiaomi.demo.document.report.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2023/7/21 3:25 下午
 */
@Data
@AllArgsConstructor
public class ExcelElement {
    private String sheetName;

    private String[] headers;

    private List<?> dataSet;
}
