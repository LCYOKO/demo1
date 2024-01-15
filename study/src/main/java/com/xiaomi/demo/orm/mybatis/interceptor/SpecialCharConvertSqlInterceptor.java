package com.xiaomi.demo.orm.mybatis.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.*;


@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})

})
public class SpecialCharConvertSqlInterceptor implements Interceptor {

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    private static final String SQL_SOURCE = "sqlSource";
    private static final String ROOT_SQL_NODE = "sqlSource.rootSqlNode";
    private static final String LIKE_KEYWORD = "like";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        MetaObject metaMappedStatement = MetaObject.forObject(statement, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        BoundSql boundSql = statement.getBoundSql(parameter);
        if (metaMappedStatement.hasGetter(SQL_SOURCE)) {
            SqlSource sqlSourceObj = (SqlSource) metaMappedStatement.getValue(SQL_SOURCE);
            Configuration configuration = statement.getConfiguration();
            Object parameterObject = boundSql.getParameterObject();
            Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
            DynamicContext context = new DynamicContext(statement.getConfiguration(), boundSql.getParameterObject());
            String sql;
            SqlSource newSqlSource = sqlSourceObj;
            // 没有占位符 单参数类型
            if (sqlSourceObj instanceof RawSqlSource) {
                RawSqlSource sqlSource = (RawSqlSource) sqlSourceObj;
                Class<? extends RawSqlSource> aClass = sqlSource.getClass();
                Field sqlField = aClass.getDeclaredField(SQL_SOURCE);
                sqlField.setAccessible(true);
                Object staticSqlSource = sqlField.get(sqlSource);
                if (staticSqlSource instanceof StaticSqlSource) {
                    Class<? extends StaticSqlSource> rawSqlSource = ((StaticSqlSource) staticSqlSource).getClass();
                    Field sqlInStatic = rawSqlSource.getDeclaredField("sql");
                    sqlInStatic.setAccessible(true);
                    String sqlStr = (String) sqlInStatic.get(staticSqlSource);
                    if (sqlStr.toLowerCase().contains(LIKE_KEYWORD)) {
                        sql = modifyLikeSqlForRawSqlSource(sqlStr, parameterObject);
                        //构建新的sqlSource;
                        newSqlSource = new StaticSqlSource(configuration, sql, ((StaticSqlSource) staticSqlSource).getBoundSql(parameterObject).getParameterMappings());
                    }
                }
            } else if (metaMappedStatement.hasGetter(ROOT_SQL_NODE)) {
                // 有占位符的类型
                SqlNode sqlNode = (SqlNode) metaMappedStatement.getValue(ROOT_SQL_NODE);
                sqlNode.apply(context);
                String contextSql = context.getSql();
                // like sql 特殊处理
                sql = modifyLikeSql(contextSql, parameterObject);
                //构建新的sqlSource;
                SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(configuration);
                newSqlSource = sqlSourceBuilder.parse(sql, parameterType, context.getBindings());
            }
            MappedStatement newMs = newMappedStatement(statement, buildNewBoundSqlSource(newSqlSource, parameterObject, context.getBindings()));
            invocation.getArgs()[0] = newMs;
        }
        return invocation.proceed();
    }

    private SqlSource buildNewBoundSqlSource(SqlSource newSqlSource, Object paramObject, Map<String, Object> objectMap) {
        BoundSql newBoundSql = newSqlSource.getBoundSql(paramObject);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            newBoundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return new SqlSourceWrapper(newBoundSql);
    }

    /**
     * 对 RawSqlSource 类型的 SQL 语句进行 Like 查询的修改
     *
     * @param sqlStr          原始 SQL 语句
     * @param parameterObject 查询参数对象
     * @return 修改后的 SQL 语句
     */
    private String modifyLikeSqlForRawSqlSource(String sqlStr, Object parameterObject) {
        MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        String[] values = metaObject.getGetterNames();
        return setValueForMetaObject(sqlStr, Arrays.asList(values), metaObject);

    }

    private String setValueForMetaObject(String sql, List<String> values, MetaObject metaObject) {
        for (String param : values) {
            Object val = metaObject.getValue(param);
            if (val != null && val instanceof String && (val.toString().contains("%") || val.toString().contains("_") || val.toString().contains("\\"))) {
                val = specialCharacterReplace(val.toString());
                metaObject.setValue(param, val);
            }
        }
        return sql;
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new
                MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.parameterMap(ms.getParameterMap());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        builder.resultOrdered(ms.isResultOrdered());
        return builder.build();
    }

    private String modifyLikeSql(String sql, Object parameterObject) {
        if (!sql.toLowerCase().contains(LIKE_KEYWORD)) {
            return sql;
        }
        List<String> replaceFiled = new ArrayList<>();
        String[] likes = sql.split(LIKE_KEYWORD);
        for (String str : likes) {
            String val = getParameterKey(str);
            if (StringUtils.isNotBlank(val)) {
                replaceFiled.add(val);
            }
        }
        // 修改参数
        MetaObject metaObject = MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        return setValueForMetaObject(sql, replaceFiled, metaObject);

    }

    /**
     * 将 % 替换成 \%
     * 将 _ 替换成 \_
     * 将 \ 替换成 \\
     *
     * @param str 编译后的sql
     * @return str
     */
    private String specialCharacterReplace(String str) {
        str = str.replace("\\", "\\\\");
        str = str.replace("%", "\\%");
        str = str.replace("_", "\\_");
        return str;
    }

    /**
     * 将 like 后的参数名取出来
     *
     * @param input 编译后sql
     * @return 参数名称
     */
    private String getParameterKey(String input) {
        String key = "";
        // 只取包含concat的那部分
        if (input.contains("concat")) {
            String[] temp = input.split("#");
            if (temp.length > 1) {
                key = temp[1];
                key = key.replace("{", "").replace("}", "").split(",")[0];
            }
        }
        return key;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    static class SqlSourceWrapper implements SqlSource {
        private final BoundSql boundSql;

        @SuppressWarnings("checkstyle:RedundantModifier")
        public SqlSourceWrapper(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}

