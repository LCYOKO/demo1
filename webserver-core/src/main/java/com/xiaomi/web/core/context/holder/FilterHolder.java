package com.xiaomi.web.core.context.holder;

import com.xiaomi.web.core.filter.Filter;
import lombok.Data;

/**
 * @author sinjinsong
 * @date 2018/5/3
 */
@Data
public class FilterHolder {
    private Filter filter;
    private String filterClass;

    public FilterHolder(String filterClass) {
        this.filterClass = filterClass;
    }
}
