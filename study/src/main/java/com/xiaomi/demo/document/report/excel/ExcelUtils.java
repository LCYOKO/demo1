package com.xiaomi.demo.document.report.excel;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.xiaomi.demo.document.report.csv.ReflectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.xiaomi.demo.document.report.excel.FileParseUtils.CellField;


/**
 * @Author liuchiyun
 * @Date 2023/7/21 3:26 下午
 */
@Slf4j
public class ExcelUtils {

    //The maximum number of cell styles was exceeded. You can define up to 4000 styles in a .xls workbook
    //同一个workbook最多设置4000个自定义样式,这里缓相同的样式
    private static final LoadingCache<CellStyleKey, CellStyle> cellStyleCache =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(2, TimeUnit.MINUTES)
                    .maximumSize(1000)
                    .build(new CacheLoader<CellStyleKey, CellStyle>() {
                        @Override
                        public CellStyle load(CellStyleKey cellStyleKey) throws Exception {
                            CellStyle cellStyle = cellStyleKey.getWorkbook().createCellStyle();
                            cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat(cellStyleKey.getFormat()));
                            return cellStyle;
                        }
                    });

    /**
     * 解析Excel文件
     */
    public static <T> List<T> parseAsList(File file, Class<T> itemClass) throws FileNotFoundException {
        return parseAsList(file, itemClass, 0, 0);
    }

    public static <T> List<T> parseAsList(File file, Class<T> itemClass, int sheetNum, int headRow) throws FileNotFoundException {
        return parseAsList(new FileInputStream(file), file.getName(), itemClass, sheetNum, headRow);
    }

    public static <T> List<T> parseAsList(File file, Class<T> itemClass, int sheetNum, int headRow, int skipTailRows) throws FileNotFoundException {
        return parseAsList(new FileInputStream(file), file.getName(), itemClass, sheetNum, headRow, skipTailRows);
    }

    public static <T> List<T> parseAsList(InputStream in, String fileName, Class<T> itemClass) {
        return parseAsList(in, fileName, itemClass, 0, 0);
    }

    public static <T> List<T> parseAsList(InputStream in, String fileName, Class<T> itemClass, int sheetNum, int headRow) {
        return parseAsList(in, fileName, itemClass, sheetNum, headRow, 0);
    }

    public static <T> List<T> parseAsList(InputStream in, String fileName, Class<T> itemClass, int sheetNum, int headRow, int skipTailRows) {
        List<T> list = new ArrayList<>();
        Workbook workbook = getWorkBook(in, fileName);
        if (workbook == null) {
            return list;
        }
        Sheet sheet = workbook.getSheetAt(sheetNum);
        if (sheet == null) {
            return list;
        }
        List<String> headers = new ArrayList<>();
        headRow = Math.max(headRow, sheet.getFirstRowNum());
        Row firstRow = sheet.getRow(headRow);
        firstRow.iterator().forEachRemaining(cell -> headers.add(cell.getStringCellValue().trim()));
        log.info("File header:{} ", headers);
        try {
            List<CellField> fields = sortFieldByAnnotation(itemClass, headers.toArray(new String[0]));
            for (int rowNum = headRow + 1; rowNum <= sheet.getLastRowNum() - skipTailRows; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                boolean f = true;
                T item = ReflectionUtils.newInstance(itemClass);
                for (int i = 0; i < fields.size(); i++) {
                    CellField cellField = fields.get(i);
                    Cell cell = row.getCell(i);
                    Object value = getCellValue(cell);
                    if (cellField == null || value == null) {
                        continue;
                    }
                    try {
                        FileParseUtils.injectValue(cellField, item, value);
                    } catch (Exception e) {
                        if (cellField.isRequired()) {
                            log.error("Inject required field: {} failed. cell:{}, value: {}", cellField.header, cell, value, e);
                            throw new RuntimeException(StrUtil.format("[Row:{}:{}] is parsed error, possible reason:{}", rowNum, cellField.header, e.getMessage()), e);
                        }
                    }
                    f = false;
                }
                if (f) {
                    continue;
                }
                list.add(item);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("Failed to parse excel file as List<{}>, fileName:{}", itemClass.getSimpleName(), fileName), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return list;
    }

    private static Workbook getWorkBook(InputStream in, String fileName) {
        Workbook workbook = null;
        if (fileName == null) {
            fileName = Type.XLS.code;
        }
        try {
            if (fileName.endsWith(Type.XLS.code)) {
                workbook = new HSSFWorkbook(in);
            } else if (fileName.endsWith(Type.XLSX.code)) {
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
        return workbook;
    }

    private static Workbook getWorkBook(Type type) {
        if (Type.XLS == type) {
            return new HSSFWorkbook();
        } else {
            return new XSSFWorkbook();
        }
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();

        if (cellType == CellType.BLANK) {
            return null;
        } else if (cellType == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.ERROR) {
            return cell.getErrorCellValue();
        } else if (cellType == CellType.FORMULA) {
            try {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            } catch (IllegalStateException e) {
                return cell.getRichStringCellValue();
            }
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        } else {
            return null;
        }
    }

    /**
     * 导出Excel文件
     */
    public static <T> void exportExcel(String[] headers, Collection<T> dataSet, File file) {
        try {
            exportExcel(headers, dataSet, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("export Excel failed", e);
        }
    }

    public static <T> byte[] exportExcelToByteArray(String[] headers, Collection<T> dataSet) {
        byte[] bytes = new byte[0];
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet();
            writeToSheet(sheet, headers, dataSet);
            bytes = getByteArray(workbook);
        } catch (IOException e) {
            log.error("An IOException occurred when closing the workbook", e);
        }
        return bytes;
    }

    public static byte[] getByteArray(Workbook workbook) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bt = null;
        try {
            workbook.write(baos);
            baos.flush();
            bt = baos.toByteArray();
        } catch (IOException e) {
            log.error(e.toString(), e);
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(baos);
        }
        return bt;
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataSet, OutputStream out) {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            OutputStream outputStream = new BufferedOutputStream(out);
            //预留100作为头部
            int maxLen = 65536 - 100;
            if (!CollectionUtils.isEmpty(dataSet) && dataSet.size() > maxLen) {
                List<List<T>> lists = Lists.partition((List<T>) dataSet, maxLen);
                //遍历生成sheet并读入
                for (List<T> list : lists) {
                    HSSFSheet sheet = workbook.createSheet();
                    writeToSheet(sheet, headers, list);
                }
            } else {
                //当条数小于 65536-100 时依然按照原来的逻辑执行
                HSSFSheet sheet = workbook.createSheet();
                writeToSheet(sheet, headers, dataSet);
            }
            try {
                workbook.write(out);
                outputStream.write(workbook.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
        } catch (IOException e) {
            log.error("An IOException occurred when closing the workbook", e);
        }
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataSet, OutputStream out, int minWidthFactor) {
        //XSSFWorkbook Excel2007之后的版本
        //HSSFWorkbook Excel2003之前（包含）的版本
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            //预留100作为头部
            int maxLen = 65536 - 100;
            if (!CollectionUtils.isEmpty(dataSet) && dataSet.size() > maxLen) {
                List<List<T>> lists = Lists.partition((List<T>) dataSet, maxLen);
                //遍历生成sheet并读入
                for (List<T> list : lists) {
                    XSSFSheet sheet = workbook.createSheet();
                    writeToSheet(sheet, headers, list, minWidthFactor);
                }
            } else {
                //当条数小于 65536-100 时依然按照原来的逻辑执行
                XSSFSheet sheet = workbook.createSheet();
                writeToSheet(sheet, headers, dataSet, minWidthFactor);
            }
            try {
                workbook.write(out);
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
        } catch (IOException e) {
            log.error("An IOException occurred when closing the workbook", e);
        }
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataSet, HttpServletResponse response, String fileName) {
        if (Strings.isNullOrEmpty(fileName)) {
            fileName = System.currentTimeMillis() + ".xls";
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        try {
            exportExcel(headers, dataSet, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Export Excel fail", e);
        }
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataSet, HttpServletResponse response, String fileName, int minWidthFactor) {
        if (Strings.isNullOrEmpty(fileName)) {
            fileName = System.currentTimeMillis() + ".xls";
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        try {
            exportExcel(headers, dataSet, response.getOutputStream(), minWidthFactor);
        } catch (IOException e) {
            throw new RuntimeException("Export Excel fail", e);
        }
    }

    /**
     * Excel导出，根据注解自动生成header
     */
    public static <T> void exportExcel(Class clazz, Collection<T> dataSet, HttpServletResponse response, String fileName) {
        exportExcel(getCellHeaders(clazz), dataSet, response, fileName);
    }

    private static String[] getCellHeaders(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Stream.of(fields)
                .filter(f -> f.getAnnotation(ExcelCell.class) != null)
                .map(f -> {
                    ExcelCell cell = f.getAnnotation(ExcelCell.class);
                    return cell.value();
                }).toArray(String[]::new);
    }

    /**
     * 导出带有多个sheet的Excel文件
     */
    public static void exportExcel(List<ExcelElement> excelElements, File file) {
        exportExcel(excelElements, file, Type.XLS);
    }

    /**
     * 导出带有多个sheet的Excel文件
     */
    public static void exportExcel(List<ExcelElement> excelElements, File file, Type excelType) {
        try {
            exportExcel(excelElements, new FileOutputStream(file), excelType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("export Excel failed", e);
        }
    }

    public static void exportExcel(List<ExcelElement> excelElements, OutputStream outputStream, Type excelType) {
        Workbook workbook = getWorkBook(excelType);
        excelElements.forEach(element -> {
            Sheet sheet;
            if (!org.springframework.util.StringUtils.isEmpty(element.getSheetName())) {
                sheet = workbook.createSheet(element.getSheetName());
            } else {
                sheet = workbook.createSheet();
            }
            writeToSheet(sheet, element.getHeaders(), element.getDataSet());
        });
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    public static void exportExcel(List<ExcelElement> excelElements, OutputStream outputStream) {
        exportExcel(excelElements, outputStream, Type.XLS);
    }

    public static <T> void writeToSheet(Sheet sheet, String[] headers, Collection<T> dataSet) {
        writeToSheet(sheet, headers, dataSet, 0, true, true);
    }

    public static <T> void writeToSheet(Sheet sheet, String[] headers, Collection<T> dataSet, int minWidthFactor) {
        writeToSheet(sheet, headers, dataSet, 0, true, true, minWidthFactor);
    }

    public static <T> void writeToSheet(Sheet sheet, String[] headers, Collection<T> dataSet, int startIndex, boolean autoSizeColumn, boolean addHeaderRecord) {
        Preconditions.checkArgument(startIndex >= 0, "startIndex less than 0");
        if (addHeaderRecord && headers != null) {
            Row row = sheet.createRow(startIndex);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                RichTextString text = sheet instanceof HSSFSheet ? new HSSFRichTextString(headers[i]) : new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
        }
        if (dataSet.isEmpty()) {
            return;
        }
        T data = dataSet.iterator().next();
        List<CellField> fieldList = sortFieldByAnnotation(data.getClass(), headers);
        Iterator<T> it = dataSet.iterator();
        int index = addHeaderRecord ? startIndex : startIndex - 1;
        while (it.hasNext()) {
            index++;
            Row row = sheet.createRow(index);
            T t = it.next();
            try {
                for (int i = 0; i < fieldList.size(); i++) {
                    if (Objects.isNull(fieldList.get(i))) {
                        continue;
                    }
                    CellField cellField = fieldList.get(i);
                    Cell cell = row.createCell(i);
                    setCellValue(cell, cellField.field, t);
                }
            } catch (Exception e) {
                log.error(e.toString(), e);
            }
        }
        if (autoSizeColumn && headers != null) {
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    public static <T> void writeToSheet(Sheet sheet, String[] headers, Collection<T> dataSet, int startIndex, boolean autoSizeColumn, boolean addHeaderRecord, int minWidthFactor) {
        Preconditions.checkArgument(startIndex >= 0, "startIndex less than 0");
        if (addHeaderRecord && headers != null) {
            Row row = sheet.createRow(startIndex);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                RichTextString text = sheet instanceof HSSFSheet ? new HSSFRichTextString(headers[i]) : new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
        }
        if (dataSet.isEmpty()) {
            return;
        }
        T data = dataSet.iterator().next();
        List<CellField> fieldList = sortFieldByAnnotation(data.getClass(), headers);
        Iterator<T> it = dataSet.iterator();
        int index = addHeaderRecord ? startIndex : startIndex - 1;
        while (it.hasNext()) {
            index++;
            Row row = sheet.createRow(index);
            T t = it.next();
            try {
                for (int i = 0; i < fieldList.size(); i++) {
                    if (Objects.isNull(fieldList.get(i))) {
                        continue;
                    }
                    CellField cellField = fieldList.get(i);
                    Cell cell = row.createCell(i);
                    setCellValue(cell, cellField.field, t);
                }
            } catch (Exception e) {
                log.error(e.toString(), e);
            }
        }
        if (autoSizeColumn && headers != null) {
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i, true);
                if (sheet.getColumnWidth(i) < headers[i].length() * minWidthFactor) {
                    sheet.setColumnWidth(i, headers[i].length() * minWidthFactor);
                }
            }
        }
    }

    private static <T> void setCellValue(Cell cell, Field field, T data) throws IllegalAccessException {
        field.setAccessible(true);
        ExcelCell ann = field.getAnnotation(ExcelCell.class);
        Object value = field.get(data);
        String textValue = null;
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            cell.setCellValue(intValue);
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(fValue);
        } else if (value instanceof Double) {
            double dValue = (Double) value;
            cell.setCellValue(dValue);
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
            cell.setCellType(CellType.STRING);
        } else if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            cell.setCellValue(bValue);
        } else if (value instanceof Date) {
            Date date = (Date) value;
            textValue = cn.hutool.core.date.DateUtil.format(date, getFormatter(field, DateTimeFormatter.ISO_DATE_TIME));
        } else if (value instanceof LocalDate) {
            LocalDate date = (LocalDate) value;
            textValue = date.format(getFormatter(field, DateTimeFormatter.ISO_DATE));
        } else {
            if (null != ann && null == value && !ann.required()) {
                return;
            }
            String empty = "";
            if (ann != null) {
                empty = ann.defaultValue();
            }
            textValue = value == null ? empty : value.toString();
        }
        if (textValue != null) {
            RichTextString richString = cell instanceof HSSFCell ? new HSSFRichTextString(textValue) : new XSSFRichTextString(textValue);
            cell.setCellValue(richString);
        }

        if (ann != null && !StringUtils.isEmpty(ann.format())) {
            Workbook workbook = cell.getSheet().getWorkbook();
            CellStyle cellStyle = cellStyleCache.getUnchecked(new CellStyleKey(workbook, ann.format()));
            cell.setCellStyle(cellStyle);
        }
    }

    public static DateTimeFormatter getFormatter(Field field, DateTimeFormatter defaultFormatter) {
        DateTimeFormatter formatter = defaultFormatter;
        DateTimeFormat format = field.getAnnotation(DateTimeFormat.class);
        if (format != null) {
            if (!StringUtils.isEmpty(format.pattern())) {
                formatter = DateTimeFormatter.ofPattern(format.pattern());
            } else {
                switch (format.iso()) {
                    case TIME:
                        formatter = DateTimeFormatter.ISO_LOCAL_TIME;
                        break;
                    case DATE_TIME:
                        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        break;
                    case DATE:
                        formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                        break;
                    default:
                        return defaultFormatter;
                }
            }
        }
        return formatter;
    }

    private static List<CellField> sortFieldByAnnotation(Class<?> clazz, String[] headers) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, CellField> fieldMap = Stream.of(fields)
                .filter(f -> f.getAnnotation(ExcelCell.class) != null)
                .map(f -> {
                    ExcelCell cell = f.getAnnotation(ExcelCell.class);
                    return new CellField(cell.value(), cell.required(), f, cell.formats());
                }).collect(Collectors.toMap(CellField::getHeader, Function.identity()));
        return Stream.of(headers).map(fieldMap::get).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class CellStyleKey {
        private Workbook workbook;
        private String format;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CellStyleKey that = (CellStyleKey) o;
            return Objects.equals(workbook, that.workbook) &&
                    Objects.equals(format, that.format);
        }

        @Override
        public int hashCode() {
            return Objects.hash(workbook, format);
        }
    }

    public enum Type {
        XLS("xls"),
        XLSX("xlsx");

        public final String code;

        Type(String code) {
            this.code = code;
        }
    }
}
