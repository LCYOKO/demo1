package com.xiaomi.demo.document.pdf;

import java.io.File;
import java.io.OutputStream;

/**
 * @Author: liuchiyun
 * @Date: 2023/9/28
 */
public interface FileConverter {
    /**
     * 文件渲染
     *
     * @param file         源文件
     * @param outputStream 输出流
     */
    void render(File file, OutputStream outputStream);
}
