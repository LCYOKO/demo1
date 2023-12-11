package com.xiaomi.demo.orm.mybatis.plugin;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public class HYCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private SimpleDateFormat dateFormat;

    public HYCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        String comment = introspectedColumn.getRemarks();

        Set<Annotations> markedAnnotations = getMarkedAnnotationByComment(comment);
        for (Annotations a : markedAnnotations) {
            field.addAnnotation(a.atName);
            comment = StringUtils.replace(comment, a.atName, StringUtils.EMPTY).trim();
        }

        if (StringUtils.isNotBlank(comment)) {
            field.addJavaDocLine(new StringBuilder(" * ").append(comment).toString());
        }
        field.addJavaDocLine(new StringBuilder(" * ").append(MergeConstants.NEW_ELEMENT_TAG).append(" ").append(introspectedTable.getFullyQualifiedTable()).append('.').append(introspectedColumn.getActualColumnName()).toString());
        field.addJavaDocLine(" */");
    }

    @Nonnull
    private Set<Annotations> getMarkedAnnotationByComment(String comment) {
        if (StringUtils.isBlank(comment)) {
            return Collections.emptySet();
        }

        return Arrays.stream(Annotations.values())
                .filter(a -> comment.contains(a.atName))
                .collect(Collectors.toSet());
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        addJavaDoc(field, introspectedTable);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        Arrays.stream(Annotations.values()).forEach(a -> topLevelClass.addImportedType(a.javaType));
        topLevelClass.addJavaDocLine("/**");
        if (StringUtils.isNotBlank(introspectedTable.getRemarks())) {
            topLevelClass.addJavaDocLine(new StringBuilder(" * ").append(introspectedTable.getRemarks()).toString());
        }
        topLevelClass.addJavaDocLine(new StringBuilder(" * ").append(MergeConstants.NEW_ELEMENT_TAG).append(" ").append(introspectedTable.getFullyQualifiedTable()).toString());
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addJavaDoc(innerClass, introspectedTable);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        if (b) {
            innerClass.addJavaDocLine(" * WARNING - do_not_delete_during_merge");
        }
        innerClass.addJavaDocLine(new StringBuilder(" * ").append(MergeConstants.NEW_ELEMENT_TAG).append(" ").append(introspectedTable.getFullyQualifiedTable()).toString());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        addJavaDoc(innerEnum, introspectedTable);
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        addJavaDoc(method, introspectedTable);
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--"));

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));

        String s = getDateString();
        if (s != null) {
            sb.setLength(0);
            sb.append("  This element was generated on ");
            sb.append(s);
            sb.append('.');
            xmlElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(new TextElement("-->"));
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    private void addJavaDoc(JavaElement element, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        element.addJavaDocLine("/**");
        element.addJavaDocLine(new StringBuilder(" * ").append(MergeConstants.NEW_ELEMENT_TAG).append(" ").append(introspectedTable.getFullyQualifiedTable()).toString());
        element.addJavaDocLine(" */");
    }

    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    private enum Annotations {
        DEPRECATED("@Deprecated", "java.lang.Deprecated"),
        NONNULL("@Nonnull", "javax.annotation.Nonnull"),
        NULLABLE("@Nullable", "javax.annotation.Nullable"),
        ;

        private final String atName;
        private final String className;
        private final FullyQualifiedJavaType javaType;

        Annotations(String atName, String className) {
            this.atName = atName;
            this.className = className;
            this.javaType = new FullyQualifiedJavaType(className);
        }
    }

}
