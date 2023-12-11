package com.xiaomi.demo.orm.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 */
public class CloneablePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            columns = introspectedTable.getNonBLOBColumns();
        } else {
            columns = introspectedTable.getAllColumns();
        }
        implementCloneable(topLevelClass);
        overrideClone(topLevelClass, columns);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        implementCloneable(topLevelClass);
        overrideClone(topLevelClass, introspectedTable.getPrimaryKeyColumns());
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        implementCloneable(topLevelClass);
        overrideClone(topLevelClass, introspectedTable.getAllColumns());
        return true;
    }

    private void implementCloneable(TopLevelClass topLevelClass) {
        FullyQualifiedJavaType cloneable = new FullyQualifiedJavaType("java.lang.Cloneable");
        topLevelClass.addSuperInterface(cloneable);
    }

    private void overrideClone(TopLevelClass topLevelClass, List<IntrospectedColumn> columns) {
        final Method cloneMethod = buildCloneMethod(topLevelClass, columns);
        topLevelClass.addMethod(cloneMethod);
    }

    private Method buildCloneMethod(TopLevelClass topLevelClass, List<IntrospectedColumn> columns) {
        FullyQualifiedJavaType returnType = topLevelClass.getType();

        Method clone = new Method("clone");
        clone.setVisibility(JavaVisibility.PUBLIC);
        clone.setReturnType(returnType);
        clone.addAnnotation("@Override");

        String clonedObjectName = "cloned";

        String newObject = returnType.getShortName() + " " + clonedObjectName +
                " = new " + returnType.getShortName() + "();";
        clone.addBodyLine(newObject);

        for (IntrospectedColumn column : columns) {
            String assignField = assignField(clonedObjectName, column);
            clone.addBodyLine(assignField);
        }

        String returnObject = "return " + clonedObjectName + ";";
        clone.addBodyLine(returnObject);

        return clone;
    }

    private String assignField(String objectName, IntrospectedColumn column) {
        final String fieldName = column.getJavaProperty();
        String assignField;
        // 对于可变对象，这里的赋值要进行深度复制
        if ("java.util.Date".equals(column.getFullyQualifiedJavaType().getFullyQualifiedName())) {
            assignField = assignDateField(objectName, fieldName);
        } else {
            assignField = assignImmutableField(objectName, fieldName);
        }
        return assignField;
    }

    private String assignDateField(String objectName, String fieldName) {
        return String.format("%s.%s = (this.%s == null ? null : new Date(this.%s.getTime()));",
                objectName, fieldName, fieldName, fieldName);
    }

    private String assignImmutableField(String objectName, String fieldName) {
        return String.format("%s.%s = this.%s;", objectName, fieldName, fieldName);
    }
}
