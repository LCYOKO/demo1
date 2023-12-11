package com.xiaomi.demo.document.report.word.poi;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.DatePattern;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Function;

/**
 * @Author:柳维民
 * @Date: 2023/9/27 17:39
 */
public enum TransformEnum {

    /**
     * 默认什么都不处理
     */
    TRANSFORM_DEFAULT(0, string -> ((String) string).replaceAll(" ", "")),


    /**
     *
     */
    TRANSFORM_CHINESE_MONEY_2_DECIMAL(1, moneyStr -> {
        if (StringUtils.isNotBlank((CharSequence) moneyStr)) {
            try {
                BigDecimal actualAmount = new BigDecimal(NumberChineseFormatter.chineseToNumber((String) moneyStr));
                return actualAmount.divide(new BigDecimal("10000"), 4, BigDecimal.ROUND_HALF_UP);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }),

    TRANSFORM_TIME_STR_2_DATE(2, timeStr -> {
        if (StringUtils.isNotBlank((CharSequence) timeStr)) {
            String chineseDate = ((String) timeStr).replaceAll(" ", "");
            // 将格式转换为yyyy年MM月dd日格式,不足的填0, 比如2023年10月8日 ---》 2023年10月8日
            chineseDate = chineseDate.length() == 11 ? chineseDate : String.valueOf(getChars(chineseDate));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.CHINESE_DATE_PATTERN);
            try {
                return Date.from(LocalDate.parse(chineseDate, formatter).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    });

    @Nonnull
    private static char[] getChars(String chineseDate) {
        if (chineseDate.contains("年") && chineseDate.contains("月") && chineseDate.contains("日")) {
            if ("年月日".equals(chineseDate)) {
                return new char[0];
            }
            char[] standardDataChar = new char[11];
            char[] chars = chineseDate.toCharArray();
            int standardIndex = 0;
            for (int i = 0; i < chars.length; i++) {
                standardDataChar[standardIndex] = chars[i];
                if ((chars[i] == '年' | chars[i] == '月')
                        && (chars[i + 2] == '月' | chars[i + 2] == '日')
                ) {
                    standardDataChar[standardIndex + 1] = '0';
                    standardIndex++;
                }
                standardIndex++;
            }
            return standardDataChar;
        }
        return new char[0];
    }


    int code;

    Function function;


    TransformEnum(int code, Function function) {
        this.code = code;
        this.function = function;
    }
}
