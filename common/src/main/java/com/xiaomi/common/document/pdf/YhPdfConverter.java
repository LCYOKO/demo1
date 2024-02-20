package com.xiaomi.common.document.pdf;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/26
 */
@Slf4j
public class YhPdfConverter {
    private FileConverter word2PdfConverter;
    private FileConverter excel2PdfConverter;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public YhPdfConverter() {
        word2PdfConverter = new Word2PdfConverter();
        excel2PdfConverter = new Excel2PdfConverter();
    }

    @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:WhitespaceAround"})
    public void renderFile2Pdf(@Nonnull File file, @Nonnull OutputStream outputStream) {
        String fileName = file.getName();
        FileType fileType = FileType.getOrThrow(fileName);
        try {
            switch (fileType) {
                case DOC:
                case DOCX:
                    word2PdfConverter.render(file, outputStream);
                    break;
                case XLS:
                case XLSX:
                    excel2PdfConverter.render(file, outputStream);
                    break;
                case CSV:
                    throw new RuntimeException("renderCsv2Pdf not support");
                case PDF:
                    throw new RuntimeException("renderPdf2Pdf not support");
                case PIC:
                    throw new RuntimeException("renderPicture2Pdf not support");
                default:
            }
        } catch (Exception ex) {
            log.error("renderFile2Pdf failed. file:{}", file, ex);
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private enum FileType {
        DOC,
        DOCX,
        XLS,
        XLSX,
        CSV,
        PDF,
        PIC;
        private static final Map<String, FileType> STRING_FILE_TYPE_MAP;

        static {
            STRING_FILE_TYPE_MAP = new HashMap<>(16);

            // word
            STRING_FILE_TYPE_MAP.put("doc", DOC);
            STRING_FILE_TYPE_MAP.put("docx", DOCX);

            // excel
            STRING_FILE_TYPE_MAP.put("xls", XLS);
            STRING_FILE_TYPE_MAP.put("xlsx", XLSX);

            // csv
            STRING_FILE_TYPE_MAP.put("csv", CSV);

            // pdf
            STRING_FILE_TYPE_MAP.put("pdf", PDF);

            // pic
            STRING_FILE_TYPE_MAP.put("jpg", PIC);
            STRING_FILE_TYPE_MAP.put("png", PIC);
        }

        @SuppressWarnings("checkstyle:WhitespaceAround")
        public static FileType getOrThrow(String fileName) {
            String fileType = fileName.substring(fileName.indexOf(".") + 1);
            return Optional.ofNullable(STRING_FILE_TYPE_MAP.get(fileType)).orElseThrow(() -> new IllegalArgumentException("非法文件类型: " + fileType));
        }
    }

}
