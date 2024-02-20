package com.xiaomi.common.document.report.word.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @Author:柳维民
 * @Date: 2023/9/25 15:02
 */
@Slf4j
public class DocxParser {

    private static final Map<Integer, ValueHandler> valueHandlerMap = new HashMap<>();


    static {
        valueHandlerMap.put(SourceFromTypeEnum.SOURCE_FROM_TABLE.getCode(), new DocTableHandler());
        valueHandlerMap.put(SourceFromTypeEnum.SOURCE_FROM_PARAGRAPH.getCode(), new DocParagraphHandler());
    }

    public static <T> T parseDocxFile(InputStream fileInputStream, Class<T> returnClass) {

        XWPFDocument xwpfDocument;
        try {
            xwpfDocument = new XWPFDocument(fileInputStream);
        } catch (Exception e) {
            log.info("解析docx文件========》获取文件异常");
            throw new RuntimeException("解析docx文件========》获取文件异常");
        }

        try {
            T t = returnClass.newInstance();
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                DocxPosition annotation = field.getAnnotation(DocxPosition.class);
                String targetContent = getTargetContent(annotation, xwpfDocument);
                boolean needTransform = annotation.needTransform();
                if (needTransform) {
                    Object result = annotation.transformAction().function.apply(targetContent);
                    field.set(t, result);
                } else {
                    field.set(t, targetContent);
                }
            }
            return t;
        } catch (Exception e) {
            log.info("生成自定对象失败" + e.getMessage());
            return null;
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.info("关闭文件流异常" + e.getMessage());
            }
        }
    }


    private static String getTargetContent(DocxPosition annotation, XWPFDocument document) {
        SourceFromTypeEnum sourceFromTypeEnum = annotation.sourceFromType();
        ValueHandler valueHandler = valueHandlerMap.get(sourceFromTypeEnum.getCode());
        if (Objects.nonNull(valueHandler)) {
            return valueHandler.getTargetValueFromSource(annotation, document);
        }
        return "";
    }

}
