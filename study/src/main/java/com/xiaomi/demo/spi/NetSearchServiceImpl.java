package com.xiaomi.demo.spi;

import java.util.List;

/**
 * @author l
 * @create 2020-12-15-23:09
 */
public class NetSearchServiceImpl implements SearchService {
    @Override
    public List<String> getAllFiles(String query) {
        System.out.println("get answer"+query);
        return null;
    }
}
