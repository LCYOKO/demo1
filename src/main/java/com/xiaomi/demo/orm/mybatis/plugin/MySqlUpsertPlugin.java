package com.xiaomi.demo.orm.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;


/**
 * @Author liuchiyun
 * @Date 2023/7/25 2:06 下午
 * ref https://github.com/beihaifeiwu/dolphin
 */
public class MySqlUpsertPlugin extends com.jxyh.common.mybatis.plugins.AbstractUpsertPlugin {

    @Override
    protected void generateSqlMapContent(IntrospectedTable introspectedTable, XmlElement parent) {
        generateTextBlockAppendTableName("insert into ", introspectedTable, parent);
        generateActualColumnNamesWithParenthesis(introspectedTable.getAllColumns(), parent);
        generateTextBlock("values ", parent);
        generateParametersSeparateByCommaWithParenthesis(PROPERTY_PREFIX, introspectedTable.getAllColumns(), parent);
        generateTextBlock("on duplicate key update ", parent);
        generateParameterForSet(PROPERTY_PREFIX, introspectedTable.getAllColumns(), parent);
    }

    @Override
    protected void generateSqlMapContentSelective(IntrospectedTable introspectedTable, XmlElement parent) {
        generateTextBlockAppendTableName("insert into ", introspectedTable, parent);
        generateActualColumnNamesWithParenthesis(PROPERTY_PREFIX, true, introspectedTable.getAllColumns(), parent);
        generateTextBlock("values ", parent);
        generateParametersSeparateByCommaWithParenthesis(PROPERTY_PREFIX, true, introspectedTable.getAllColumns(), parent);
        generateTextBlock("on duplicate key update ", parent);
        generateParameterForSet(PROPERTY_PREFIX, true, introspectedTable.getAllColumns(), parent);
    }
}
