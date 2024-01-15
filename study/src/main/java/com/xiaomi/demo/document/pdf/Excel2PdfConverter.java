package com.xiaomi.demo.document.pdf;

import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/28
 */
@Slf4j
public class Excel2PdfConverter implements FileConverter {
    private boolean validLicense = false;

    {
        try (InputStream is = com.aspose.cells.License.class.getResourceAsStream("/com.aspose.cells.lic_2999.xml");) {
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            is.close();
            validLicense = true;
        } catch (Exception e) {
            log.error("get license failed.", e);
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public void render(File file, OutputStream outputStream) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!validLicense) {
            return;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook wb = new Workbook(fileInputStream);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            wb.save(outputStream, pdfSaveOptions);
        } catch (Exception ex) {
            log.error("render excel to pdf failed. file:{}", file, ex);
        }
    }

    /**
     * 设置打印的sheet 自动拉伸比例
     *
     * @param wb Workbook
     */
    private void autoDraw(Workbook wb) {
        for (int i = 0; i < wb.getWorksheets().getCount(); i++) {
            wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
            wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
        }
    }

    /**
     * 隐藏workbook中不需要的sheet页。
     *
     * @param wb   Workbook
     * @param page 显示页的sheet数组
     */
    private void printSheetPage(Workbook wb, int[] page) {
        for (int i = 1; i < wb.getWorksheets().getCount(); i++) {
            wb.getWorksheets().get(i).setVisible(false);
        }
        if (null == page || page.length == 0) {
            wb.getWorksheets().get(0).setVisible(true);
        } else {
            for (int i = 0; i < page.length; i++) {
                wb.getWorksheets().get(i).setVisible(true);
            }
        }
    }
}
