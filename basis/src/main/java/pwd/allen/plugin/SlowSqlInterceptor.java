package pwd.allen.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义慢SQL统计插件
 *
 * @author lenovo
 * @create 2020-08-14 8:56
 **/
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})
        , @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
        , @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SlowSqlInterceptor implements Interceptor {

    private Integer limitMillis;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long beginTimeMillis = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        try {
            return invocation.proceed();
        } finally {
            long endTimeMillis = System.currentTimeMillis();
            long costTimeMillis = endTimeMillis - beginTimeMillis;
            if (costTimeMillis > limitMillis) {
                BoundSql boundSql = statementHandler.getBoundSql();
                System.out.println(String.format("SQL语句【%s】，执行耗时：%d ms", boundSql, costTimeMillis));
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.limitMillis = Integer.parseInt(properties.getProperty("limitMillis", "1000"));
    }
}
