package com.xiaomi.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: liuchiyun
 * @Date: 2024/11/25
 */
public class PdfTest {

    @Test
    public void test1() throws IOException {
        URL url = getClass().getClassLoader().getResource("test.pdf");
        assert url != null;
        File pdfFile = new File(url.getFile());
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        StringBuilder text = new StringBuilder();
        text.append(stripper.getText(document));
        System.out.println(text);
    }

    @Test
    public void test2() {
//        Message systemMsg = Message.builder()
//                .role(Role.SYSTEM.getValue())
//                .content("You are a helpful assistant.")
//                .build();
//        Message userMsg = Message.builder()
//                .role(Role.USER.getValue())
//                .content("你是谁？")
//                .build();
//        ApplicationParam param = ApplicationParam.builder()
//                .apiKey("YOUR_API_KEY")
//                .appId("67f2148292234593a19f8bb0366ffe67")
//                .build();
    }


    @Test
    public void test3() throws IOException {
    }
}
