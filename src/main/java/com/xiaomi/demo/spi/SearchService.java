package com.xiaomi.demo.spi;

import java.util.List;

/**
 * @author l
 * @create 2020-12-15-23:08
 */
public interface SearchService {
   List<String> getAllFiles(String query);
}
