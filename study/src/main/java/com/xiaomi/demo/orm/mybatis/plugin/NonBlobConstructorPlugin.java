package com.xiaomi.demo.orm.mybatis.plugin;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 * 表中有Blob字段时 生成无Blob字段的构造方法
 */
public class NonBlobConstructorPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        if (CollectionUtils.isNotEmpty(introspectedTable.getBLOBColumns())) {
            List<IntrospectedColumn> columns = introspectedTable.getNonBLOBColumns();
            overrideConstructorMethod(topLevelClass, introspectedTable, columns);
        }
        return true;
    }

    private void overrideConstructorMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, List<IntrospectedColumn> columns) {
        final Method constructorMethod = buildNonBlobConstructorMethod(introspectedTable, columns);
        topLevelClass.addMethod(constructorMethod);
    }

    private Method buildNonBlobConstructorMethod(IntrospectedTable introspectedTable, List<IntrospectedColumn> columns) {
        Method constructor = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        constructor.setConstructor(true);
        constructor.setVisibility(JavaVisibility.PUBLIC);
        for (IntrospectedColumn column : columns) {
            constructor.addParameter(new Parameter(column.getFullyQualifiedJavaType(), column.getJavaProperty()));
            String assignField = "this." + column.getJavaProperty() + " = " + column.getJavaProperty() + ";";
            constructor.addBodyLine(assignField);
        }
        return constructor;
    }
}
