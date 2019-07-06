package pwd.allen.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

/**
 * 插件签名：告诉MyBatis当前插件用来拦截哪些对象的哪些方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)
})
/**
 * 在四大对象创建的时候，每个创建出来的对像都经过interceptorChain.pluginAll(parameterHandler);
 * 1.获取到所有的Interceptor（拦截器，实现Interceptor接口）
 * 2.调用interceptor.plugin(target);返回target包装后的对象
 * 插件机制就是为目标对象创建代理对象，AOP
 *
 * 插件编写：
 * 1.编写Interceptor的实现类
 * 2.使用@Interceptor注解完成插件签名
 * 3.将写好的插件注册到全局配置文件中
 * @author 门那粒沙
 * @create 2019-07-06 21:28
 **/
public class MyPlugin implements Interceptor {

    /**
     * 拦截目标对象的目标方法的执行；
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("方法执行前拦截：" + invocation.getMethod());

        //执行目标方法
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        //借助Plugin的wrap方法来使用当前Interceptor包装目标对象，该方法会根据注解决定拦截类和方法
        return Plugin.wrap(target, this);
    }

    /**
     * 将插件注册时的property属性设置传进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的属性：" + properties);
    }
}

