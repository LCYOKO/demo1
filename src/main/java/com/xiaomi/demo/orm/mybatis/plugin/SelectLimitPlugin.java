package com.xiaomi.demo.orm.mybatis.plugin;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 * ref https://github.com/wucao/mybatis-generator-limit-plugin/blob/master/src/main/java/com/xxg/mybatis/plugins/MySQLLimitPlugin.java
 * TODO 使用需注意顺序，以免sql拼接有问题
 */
public class SelectLimitPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 为每个Example类添加limit和offset属性已经set、get方法
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        modelExampleAddIntegerVariable(topLevelClass, integerWrapper, "offset");
        modelExampleAddIntegerVariable(topLevelClass, integerWrapper, "limit");

        return true;
    }


    /**
     * 为Mapper.xml的selectByExample添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        XmlElement ifLimitNotNullElement = getXmlElement("limit != null", null);

        XmlElement ifOffsetNotNullElement = getXmlElement("offset != null", "limit ${offset}, ${limit}");
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        XmlElement ifOffsetNullElement = getXmlElement("offset == null", "limit ${limit}");
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);
        return true;
    }

    private void modelExampleAddIntegerVariable(@Nonnull TopLevelClass topLevelClass, @Nonnull FullyQualifiedJavaType type, @Nonnull String fieldName) {
        Field offset = new Field();
        offset.setName(fieldName);
        offset.setVisibility(JavaVisibility.PROTECTED);
        offset.setType(type);
        topLevelClass.addField(offset);

        final String upName = StringUtils.capitalize(fieldName);
        final String setName = "set" + upName;
        final String getName = "get" + upName;

        Method setOffset = new Method();
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.setName(setName);
        setOffset.addParameter(new Parameter(type, fieldName));
        setOffset.addBodyLine("this." + fieldName + " = " + fieldName + ";");
        topLevelClass.addMethod(setOffset);

        Method getOffset = new Method();
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(type);
        getOffset.setName(getName);
        getOffset.addBodyLine("return " + fieldName + ";");
        topLevelClass.addMethod(getOffset);
    }

    /**
     * @param condition 条件
     * @param content   内容
     * @return eg.
     * <if test="{condition}">
     * {content}
     * </if>
     */
    private XmlElement getXmlElement(@Nonnull String condition, @Nullable String content) {
        XmlElement element = new XmlElement("if");
        element.addAttribute(new Attribute("test", condition));
        if (StringUtils.isNotBlank(content)) {
            element.addElement(new TextElement(content));
        }
        return element;
    }
}