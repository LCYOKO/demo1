package com.xiaomi.demo.document.pdf;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/28
 */
@Slf4j
public class Word2PdfConverter implements FileConverter {
    @Override
    public void render(File file, OutputStream outputStream) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Document doc = new Document(fileInputStream);
            doc.save(outputStream, SaveFormat.PDF);
        } catch (Exception ex) {
            log.error("render word to pdf failed. file:{}", file, ex);
        }
    }
}
