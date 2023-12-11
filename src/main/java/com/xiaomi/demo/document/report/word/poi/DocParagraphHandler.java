package com.xiaomi.demo.document.report.word.poi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:柳维民
 * @Date: 2023/9/26 17:37
 */
public class DocParagraphHandler implements ValueHandler {

    /**
     * 字段超过1200字符谨慎使用
     */
    @Override
    public String getTargetValueFromSource(DocxPosition docxPosition, XWPFDocument document) {
        String paragraphName = docxPosition.tableName();
        TextInfo textInfo = this.getTextInfo(document, paragraphName);

        String result = "";
        int[] phaseLocations = docxPosition.phaseLocations();
        if (phaseLocations.length > 0) {
            result = textInfo.getInfoFromPhases(phaseLocations);
        }

        int[] underLineLocations = docxPosition.underLineLocations();
        if (underLineLocations.length > 0) {
            result = textInfo.getInfoFromUnderlineContent(underLineLocations);
        }

        if (docxPosition.startWithNum()) {
            result = textInfo.getInfoStartWithNum();
        }

        // 通过keyword去获取指定的值
        String keywords = docxPosition.keywords();
        boolean userRegx = docxPosition.useRegx();
        if (userRegx && StringUtils.isNotBlank(keywords)) {
            String[] keywordArray = keywords.split("#");
            StringJoiner stringJoiner = new StringJoiner("\n");
            for (String keyword : keywordArray) {
                Pattern r = Pattern.compile(keyword);
                Matcher m = r.matcher(result);
                if (m.find()) {
                    stringJoiner.add(m.group(1));
                } else {
                    stringJoiner.add("");
                }
            }
            return stringJoiner.toString();
        }
        return result;
    }

    private TextInfo getTextInfo(XWPFDocument document, String paragraphName) {
        List<String> phases = new ArrayList<>();
        List<String> underLineContent = new ArrayList<>();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        List<XWPFParagraph> relatedParagraphs = this.getRelatedParagraphs(paragraphName, paragraphs);
        if (CollectionUtils.isNotEmpty(relatedParagraphs)) {
            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(document);
            relatedParagraphs.forEach(xwpfParagraph -> {
                extractUnderlineInfo(underLineContent, xwpfParagraph);
                StringBuilder stringBuilder = new StringBuilder();
                xwpfWordExtractor.appendParagraphText(stringBuilder, xwpfParagraph);
                phases.add(stringBuilder.toString().replace(" ", ""));
            });
        }
        return TextInfo.builder().phases(phases).underlineContent(underLineContent).build();
    }

    private static void extractUnderlineInfo(List<String> underLineContent, XWPFParagraph xwpfParagraph) {
        List<XWPFRun> runs = xwpfParagraph.getRuns();
        if (CollectionUtils.isNotEmpty(runs)) {
            StringJoiner stringJoiner = new StringJoiner("");
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                UnderlinePatterns underline = run.getUnderline();
                if (underline.equals(UnderlinePatterns.SINGLE)) {
                    String text = run.getText(0);
                    if (StringUtils.isNotBlank(text)) {
                        stringJoiner.add(text);
                    }
                } else {
                    if (i != 0 && UnderlinePatterns.SINGLE.equals(runs.get(i - 1).getUnderline())) {
                        underLineContent.add(stringJoiner.toString());
                        stringJoiner = new StringJoiner("");
                    }
                }
            }
        }
    }

    private List<XWPFParagraph> getRelatedParagraphs(String paragraphName, List<XWPFParagraph> paragraphs) {

        List<XWPFParagraph> collectdParagraph = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(paragraphs)) {
            AtomicBoolean needCollectd = new AtomicBoolean(false);
            AtomicBoolean firstValidFlag = new AtomicBoolean(false);
            paragraphs.forEach(xwpfParagraph -> {
                List<XWPFRun> runs = xwpfParagraph.getRuns();
                if (firstValidFlag.get() && !needCollectd.get()) {
                    return;
                }
                if (CollectionUtils.isNotEmpty(runs)) {
                    String text = runs.get(0).getText(0);
                    if (paragraphName.equals(text) && runs.size() == 1) {
                        needCollectd.set(true);
                        firstValidFlag.set(true);
                    }
                    if (needCollectd.get()) {
                        collectdParagraph.add(xwpfParagraph);
                    }
                    if (runs.stream().anyMatch(run -> StringUtils.isNotBlank(run.getText(0)) && run.getText(0).trim().equals("年"))
                            && runs.stream().anyMatch(run -> StringUtils.isNotBlank(run.getText(0)) && run.getText(0).trim().equals("月"))
                            && runs.stream().anyMatch(run -> StringUtils.isNotBlank(run.getText(0)) && run.getText(0).trim().equals("日"))) {
                        needCollectd.set(false);
                    }
                }
            });
        }
        return collectdParagraph;
    }


}
