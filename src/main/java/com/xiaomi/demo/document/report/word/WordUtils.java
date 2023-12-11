package com.xiaomi.demo.document.report.word;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @Author liuchiyun
 * @Date 2023/7/21 4:48 下午
 * https://blog.csdn.net/qq_43248802/article/details/130685143 word.xml各种标签含义
 */
@Slf4j
public class WordUtils {

    private WordUtils() {

    }

    private static final String SUFFIX = ".ftl";

    public static void renderAndExport(FreeMarkerConfigurer configurer, String templateName, Map<String, Object> variables, OutputStream out) throws Exception {
        Template tp = configurer.getConfiguration().getTemplate(templateName + SUFFIX, "UTF-8");
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out)) {
            tp.process(variables, outputStreamWriter);
            outputStreamWriter.flush();
        } catch (Exception e) {
            log.error("Export word failed. fileName:{}, variables:{}", templateName, variables, e);
            throw new RuntimeException(e);
        }
    }
}