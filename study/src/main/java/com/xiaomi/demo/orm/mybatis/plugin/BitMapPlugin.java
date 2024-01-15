package com.xiaomi.demo.orm.mybatis.plugin;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.ibatis2.Ibatis2FormattingUtilities;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 * description: column中使用bitmap存储"是/否"关系时查询使用
 * <table> 中配置 <property name="bitMapColumns" value="支持位查询的数据库字段列表，英文逗号分隔"> 即可支持位查询
 */
public class BitMapPlugin extends PluginAdapter {

    private static final String BIT_MAP_PROPERTY = "bitMapColumns";
    private static final Set<Integer> BIT_MAP_SUPPORT_TYPES = Sets.newHashSet(Types.BIT, Types.TINYINT, Types.SMALLINT, Types.INTEGER, Types.BIGINT);
    private static final Splitter BIT_MAP_PROPERTY_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

    @Override
    public boolean validate(List<String> warings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        InnerClass criteria = null;
        // first, find the Criteria inner class
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$
                criteria = innerClass;
                break;
            }
        }

        if (criteria == null) {
            // can't find the inner class for some reason, bail out.
            return true;
        }

        List<String> bitMapColumns = Lists.newArrayList();
        final Properties properties = introspectedTable.getTableConfiguration().getProperties();
        final String bitmapColumnsStr = properties.getProperty(BIT_MAP_PROPERTY);
        if (StringUtils.isNotBlank(bitmapColumnsStr)) {
            bitMapColumns = BIT_MAP_PROPERTY_SPLITTER.splitToList(bitmapColumnsStr);
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
            final String actualColumnName = introspectedColumn.getActualColumnName();
            if (!BIT_MAP_SUPPORT_TYPES.contains(introspectedColumn.getJdbcType()) || !bitMapColumns.contains(actualColumnName)) {
                continue;
            }
            Method equalMethod = buildBitMapMethod(introspectedColumn, true);
            Method notEqualMethod = buildBitMapMethod(introspectedColumn, false);

            criteria.addMethod(equalMethod);
            criteria.addMethod(notEqualMethod);

            // 添加 ContainsBit method
            Method containsMethod = buildContainsBitMethod(introspectedColumn, true);
            Method notContainsMethod = buildContainsBitMethod(introspectedColumn, false);

            criteria.addMethod(containsMethod);
            criteria.addMethod(notContainsMethod);
        }

        return true;
    }

    /**
     * 添加bitmap method
     */
    private Method buildBitMapMethod(IntrospectedColumn introspectedColumn, boolean isEqual) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), "value")); //$NON-NLS-1$
        method.setName(buildBitMapMethodName(introspectedColumn, isEqual));
        method.addBodyLines(buildBitMapMethodBody(introspectedColumn, isEqual));
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        return method;
    }

    private String buildBitMapMethodName(IntrospectedColumn introspectedColumn, boolean isEqual) {
        StringBuilder nameSb = new StringBuilder();
        nameSb.append(introspectedColumn.getJavaProperty());
        nameSb.setCharAt(0, Character.toUpperCase(nameSb.charAt(0)));
        nameSb.insert(0, "and");
        nameSb.append(isEqual ? "WithBitMap" : "WithoutBitMap");
        return nameSb.toString();
    }

    private List<String> buildBitMapMethodBody(IntrospectedColumn introspectedColumn, boolean isEqual) {
        List<String> bodies = new ArrayList<>();
        StringBuilder bodySb = new StringBuilder();
        bodySb.append("addCriterion(\"");
        bodySb.append(Ibatis2FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        bodySb.append(" & \" + value + \" ").append(isEqual ? "=" : "!=").append(" \", value, \"");
        bodySb.append(introspectedColumn.getJavaProperty());
        bodySb.append("\");");
        bodies.add(bodySb.toString());
        bodies.add("return (Criteria) this;");
        return bodies;
    }

    /**
     * 添加 ContainsBit method
     * <p>
     * description: column中使用 bit 存储"是/否" 关系时查询使用
     * <table> 中配置 <property name="BitContainColumns" value="支持位查询的数据库字段列表，英文逗号分隔">
     *     支持 查看是否包含某一位，
     *     举例： 搜索条件  1，2，4
     *           模版A  2      模版B  6
     *           1|2|4 = 7,    7&2 = 2 != 0,  7&6 = 6 != 0
     *           所以 模版A、B都符合条件。
     * <p>
     * 与 插件BitMapPlugin实现不同，BitMapPlugin插件实现的是 and 的功能，查询参数必须包含参数中全部的 位。
     * 此插件 实现的是 or 的功能，只需要包含参数中 其中一位便可。
     */
    private Method buildContainsBitMethod(IntrospectedColumn introspectedColumn, boolean isContain) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), "value")); //$NON-NLS-1$
        method.setName(buildContainsBitMethodName(introspectedColumn, isContain));
        method.addBodyLines(buildContainsBitMethodBody(introspectedColumn, isContain));
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        return method;
    }

    private String buildContainsBitMethodName(IntrospectedColumn introspectedColumn, boolean isContain) {
        StringBuilder nameSb = new StringBuilder();
        nameSb.append(introspectedColumn.getJavaProperty());
        nameSb.setCharAt(0, Character.toUpperCase(nameSb.charAt(0)));
        nameSb.insert(0, "and");
        nameSb.append(isContain ? "ContainsBit" : "NotContainsBit");
        return nameSb.toString();
    }

    private List<String> buildContainsBitMethodBody(IntrospectedColumn introspectedColumn, boolean isEqual) {
        List<String> bodies = new ArrayList<>();
        StringBuilder bodySb = new StringBuilder();
        bodySb.append("addCriterion(\"");
        bodySb.append(Ibatis2FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        bodySb.append(" & \" + value + \" ").append(isEqual ? "!=" : "=").append(" \", 0, \"");
        bodySb.append(introspectedColumn.getJavaProperty());
        bodySb.append("\");");
        bodies.add(bodySb.toString());
        bodies.add("return (Criteria) this;");
        return bodies;
    }
}
