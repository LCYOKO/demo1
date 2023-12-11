package com.xiaomi.demo.document.report.excel;

import cn.hutool.core.util.StrUtil;
import com.jxyh.common.report.csv.ReflectionUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * @Author liuchiyun
 * @Date 2023/7/21 3:28 下午
 */
public class FileParseUtils {

    private static final String DEFAULT_LOCAL_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd hhMMss";


    public static <T> void injectValue(CellField cellField, T target, Object originValue) {
        if (null == originValue) {
            return;
        }
        Object value = convert(cellField, originValue);
        ReflectionUtils.setField(target, cellField.field, value);
    }

    private static Object convert(CellField cellField, Object value) {
        Class<?> fieldClazz = cellField.field.getType();
        if (value.getClass() == fieldClazz) {
            return value;
        }
        String strValue = value.toString().trim();
        if (Boolean.class == fieldClazz || boolean.class == fieldClazz) {
            return parseBoolean(cellField, strValue);
        } else if (Number.class.isAssignableFrom(fieldClazz) || fieldClazz.isPrimitive()) {
            return parseNumber(cellField, strValue);
        } else if (fieldClazz == Date.class) {
            return parseDate(cellField, strValue);
        } else if (fieldClazz == LocalDate.class) {
            return parseLocalDate(cellField, strValue);
        } else {
            return strValue;
        }
    }

    private static Object parseNumber(CellField cellField, String strValue) {
        try {
            Class<?> fieldClazz = cellField.field.getType();
            Number number = NumberFormat.getNumberInstance(Locale.US).parse(strValue);
            if (byte.class == fieldClazz || Byte.class == fieldClazz) {
                return number.byteValue();
            } else if (int.class == fieldClazz || Integer.class == fieldClazz) {
                return number.intValue();
            } else if (short.class == fieldClazz || Short.class == fieldClazz) {
                return number.shortValue();
            } else if (long.class == fieldClazz || Long.class == fieldClazz) {
                return number.longValue();
            } else if (float.class == fieldClazz || Float.class == fieldClazz) {
                return number.floatValue();
            } else if (double.class == fieldClazz || Double.class == fieldClazz) {
                return number.doubleValue();
            } else if (boolean.class == fieldClazz || Boolean.class == fieldClazz) {
                return Boolean.valueOf(strValue);
            } else if (BigDecimal.class == fieldClazz) {
                return new BigDecimal(strValue);
            }
            return number;
        } catch (Exception e) {
            if (cellField.required) {
                throw new RuntimeException(StrUtil.format("Expect number for field:{},column:{}, but now it is:'{}'", cellField.field.getName(), cellField.header, strValue), e);
            } else {
                return null;
            }
        }
    }

    private static Boolean parseBoolean(CellField cellField, String strValue) {
        try {
            if (Boolean.TRUE.toString().equalsIgnoreCase(strValue) || Boolean.FALSE.toString().equalsIgnoreCase(strValue)) {
                return Boolean.valueOf(strValue);
            }
            return Integer.parseInt(strValue) > 0;
        } catch (Exception e) {
            if (cellField.required) {
                throw new RuntimeException(StrUtil.format("Expect boolean for field:{},column:{}, but now it is:'{}'", cellField.field.getName(), cellField.header, strValue), e);
            } else {
                return false;
            }
        }
    }

    private static LocalDate parseLocalDate(CellField cellField, String strValue) {
        String[] formats = getFormats(cellField, DEFAULT_LOCAL_DATE_FORMAT);
        for (String format : formats) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                return LocalDate.parse(strValue, formatter);
            } catch (Exception ignored) {

            }
        }
        if (cellField.required) {
            throw new RuntimeException(StrUtil.format("Expect date with format:{} for field:{},column:{}, but now it is:'{}'"
                    , Arrays.toString(formats), cellField.field.getName(), cellField.header, strValue));
        } else {
            return null;
        }
    }

    private static Date parseDate(CellField cellField, String strValue) {
        String[] formats = getFormats(cellField, DEFAULT_DATE_FORMAT);
        for (String format : formats) {
            SimpleDateFormat fmt = new SimpleDateFormat(format);
            try {
                return fmt.parse(strValue);
            } catch (ParseException ignored) {
            }
        }
        if (cellField.required) {
            throw new RuntimeException(StrUtil.format("Expect date with format:{} for field:{},column:{}, but now it is:'{}'"
                    , Arrays.toString(formats), cellField.field.getName(), cellField.header, strValue));
        } else {
            return null;
        }
    }


    private static String[] getFormats(CellField cellField, String defFormat) {
        if (cellField.getFormat() == null || cellField.getFormat().length == 0) {
            return new String[]{defFormat};
        }
        return cellField.getFormat();
    }


    @Data
    public static class CellField {
        public final String header;
        public final boolean required;
        public final Field field;
        public final String[] format;
    }
}