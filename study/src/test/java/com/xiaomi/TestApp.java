package com.xiaomi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.Data;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: liuchiyun
 * @Date: 2024/2/8
 */
public class TestApp {
    @Test
    public void test() {
        Map<String, Item> map = new HashMap<>();
        EasyExcel.read(new File("C:\\Users\\Admin\\Desktop\\商品历史成本.xlsx"), Item.class, new ReadListener<Item>() {
            @Override
            public void invoke(Item item, AnalysisContext analysisContext) {
                if (map.containsKey(item.getPCode())) {
                    Item old = map.get(item.getPCode());
                    if (old.getCreatedAt().before(item.getCreatedAt())) {
                        map.put(old.getPCode(), item);
                    }
                } else {
                    map.put(item.getPCode(), item);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doReadSync();

        EasyExcel.write("C:\\Users\\Admin\\Desktop\\商品历史成本1.xlsx", Item.class).sheet().doWrite(new Supplier<Collection<?>>() {
            @Override
            public Collection<Item> get() {
                return map.values();
            }
        });
    }

    @Data
    public static class Item {
        @ExcelProperty(value = "商品编码")
        private String pCode;
        @ExcelProperty("历史成本价")
        private String price;
        @ExcelProperty("创建日期")
        private Date createdAt;
    }
}
