package com.xiaomi;

import com.google.common.collect.ImmutableList;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.annotations.FieldMetadata;
import fr.opensagres.xdocreport.template.annotations.ImageMetadata;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

/**
 * @Author: liuchiyun
 * @Date: 2024/1/31
 */
@Slf4j
public class XDocxReportTest {

    @Test
    public void test() {
        String prefix = "E:\\java_code\\xdocreport.samples\\samples\\fr.opensagres.xdocreport.samples.docxandfreemarker\\src\\fr\\opensagres\\xdocreport\\samples\\docxandfreemarker";

        URL url = getClass().getClassLoader().getResource("template.docx");
        try (FileInputStream fi = new FileInputStream(url.getFile());
             FileOutputStream fo = new FileOutputStream(new File("output.docx"))) {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(fi, TemplateEngineKind.Freemarker);
            //创建xdocreport上下文对象，用于存放具体数据
            IContext context = report.createContext();
            //创建字段元数据
            FieldsMetadata metadata = report.createFieldsMetadata();
            // image
            metadata.addFieldAsImage("logo");
            // the following code is managed with @FieldMetadata( images = { @ImageMetadata( name = "photo" ) } )
            // in the DeveloperWithImage.
            metadata.addFieldAsImage("photo", "d.photo");

            //创建要替换的文本变量

            context.put("developers", ImmutableList.of(new PicData("123", new FileImageProvider(new File(prefix + "\\" + "PascalLeclercq.jpg"))), new PicData("456", new FileImageProvider(new File(prefix + "\\" + "PascalLeclercq.jpg")))));
            //把数据传输到模板中，生产对应的文件
            report.process(context, fo);
//            //2.设置填充字段、填充类以及是否为list。
//            FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
//            // 文本
//            IContext context = report.createContext();
//
//            /** *******************设置表格内容数据 BEGIN **************** */
//            List<XDocExportVo> list = new ArrayList<>();
//            // 第一行数据
//            XDocExportVo docExportVo1 = new XDocExportVo();
//            docExportVo1.setHiddenDescribe("调整前1");
//            docExportVo1.setReformDescribe("调整后1");
//            // 图片数据
//            List<PicVo> picVos = new ArrayList<>();
//            // 用本地的图片做一下测试,实际用途中需要根据自己的项目地址来
//            IImageProvider p = new FileImageProvider(new File("F:\\test_path\\cat001.jpg"));
//            // 设置图片的大小
//            // p.setSize(30f,40f);
//            PicVo picVo = new PicVo();
//            picVo.setPic(p);
//            picVos.add(picVo);
//
//            IImageProvider p1 = new FileImageProvider(new File("F:\\test_path\\cat002.jpg"));
//            PicVo picVo1 = new PicVo();
//            picVo1.setPic(p1);
//            picVos.add(picVo1);
//            docExportVo1.setHiddenPics(picVos);
//
//            List<PicVo> picVos2 = new ArrayList<>();
//            IImageProvider p3 = new FileImageProvider(new File("F:\\test_path\\xiaomaomi1.jpeg"));
//            PicVo picVo2 = new PicVo();
//            picVo2.setPic(p3);
//            picVos2.add(picVo2);
//
//            IImageProvider p4 = new FileImageProvider(new File("F:\\test_path\\xiaomaomi2.jpeg"));
//            PicVo picVo3 = new PicVo();
//            picVo3.setPic(p4);
//            picVos2.add(picVo3);
//            docExportVo1.setReformPics(picVos2);
//            list.add(docExportVo1);
//
//            // 第二行数据(这里就简单点写了)
//            XDocExportVo docExportVo2 = new XDocExportVo();
//            docExportVo2.setHiddenDescribe("调整前2");
//            docExportVo2.setReformDescribe("调整后2");
//            docExportVo2.setHiddenPics(picVos2);
//            docExportVo2.setReformPics(picVos);
//            list.add(docExportVo2);
//
//            /** *******************设置表格内容数据 END **************** */
//            // 设置表格数据
//            context.put("data", list);
//            // 设置list的数据来源模型
//            fieldsMetadata.load("data", XDocExportVo.class, true);
//
//            //特殊字符
//            fieldsMetadata.addFieldAsImage("pic1", "list1.pic", NullImageBehaviour.RemoveImageTemplate);
//            fieldsMetadata.addFieldAsImage("pic2", "list2.pic", NullImageBehaviour.RemoveImageTemplate);
//            report.setFieldsMetadata(fieldsMetadata);
//            report.process(context, fo);
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    @Data
    class XDocExportVo {

        // 描述1
        private String hiddenDescribe;

        // 描述2
        private String reformDescribe;

        // 调整前照片
        private List<PicVo> hiddenPics;

        // 调整后照片
        private List<PicVo> reformPics;

    }

    @Data
    class PicVo {
        // 模板中需要的图片
        private IImageProvider pic;
    }

    @Data
    @AllArgsConstructor
    class PicData {
        private String name;
        private IImageProvider photo;

        @FieldMetadata(images = {@ImageMetadata(name = "photo")})
        public IImageProvider getPhoto() {
            return photo;
        }
    }

}
