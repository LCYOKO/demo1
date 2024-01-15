package com.xiaomi.demo.orm.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 * ref https://github.com/beihaifeiwu/dolphin
 */
public abstract class AbstractUpsertPlugin extends AbstractXmbgPlugin {

    public static final String UPSERT = "insertOrUpdate";
    public static final String UPSERT_SELECTIVE = "insertOrUpdateSelective";

    public static final String PROPERTY_PREFIX = null;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // insertOrUpdate method
        Method upsert = new Method(UPSERT);
        upsert.setReturnType(FullyQualifiedJavaType.getIntInstance());
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();
        upsert.addParameter(new Parameter(parameterType, "record"));
        importedTypes.add(parameterType);
        interfaze.addMethod(upsert);
        context.getCommentGenerator().addGeneralMethodComment(upsert, introspectedTable);

        // insertOrUpdateSelective
        Method upsertSelective = new Method(UPSERT_SELECTIVE);
        upsertSelective.setReturnType(FullyQualifiedJavaType.getIntInstance());
        upsertSelective.addParameter(new Parameter(parameterType, "record"));
        interfaze.addMethod(upsertSelective);
        context.getCommentGenerator().addGeneralMethodComment(upsertSelective, introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();

        addSingleUpsertToSqlMap(document, introspectedTable, parameterType);
        addSingleUpsertSelectiveToSqlMap(document, introspectedTable, parameterType);
        return true;
    }

    /**
     * add update xml element to mapper.xml for upsert
     *
     * @param document          The generated xml mapper dom
     * @param introspectedTable The metadata for database table
     */
    protected void addSingleUpsertToSqlMap(Document document, IntrospectedTable introspectedTable, FullyQualifiedJavaType parameterType) {
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", UPSERT));
        update.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(update);
        generateSqlMapContent(introspectedTable, update);

        document.getRootElement().addElement(update);
    }

    protected void addSingleUpsertSelectiveToSqlMap(Document document, IntrospectedTable introspectedTable, FullyQualifiedJavaType parameterType) {
        XmlElement update = new XmlElement("update");
        update.addAttribute(new Attribute("id", UPSERT_SELECTIVE));
        update.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(update);
        generateSqlMapContentSelective(introspectedTable, update);

        document.getRootElement().addElement(update);
    }

    /**
     * 生成sqlMap里对应的xml元素
     *
     * @param introspectedTable The metadata for database table
     * @param parent            The parent element for generated xml element
     */
    protected abstract void generateSqlMapContent(IntrospectedTable introspectedTable, XmlElement parent);

    protected abstract void generateSqlMapContentSelective(IntrospectedTable introspectedTable, XmlElement parent);

}
