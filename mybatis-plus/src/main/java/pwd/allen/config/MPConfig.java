package pwd.allen.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pwd.allen.handler.MyMetaObjectHandler;
import pwd.allen.util.MyIdGenerator;

import java.util.Properties;

/**
 * @author 门那粒沙
 * @create 2020-06-21 12:57
 **/
@Configuration
public class MPConfig {

    /**
     * 分页插件，不加的话selectPage等分页方法没有分页效果
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        return paginationInterceptor;
    }

    /**
     * 自定义ID生成器
     * spring boot的话可以直接注入容器中即可
     * @return
     */
    @Bean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
        // 使用MybatisPlusPropertiesCustomizer
        return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new MyIdGenerator());
    }


    /**
     * 自定义公共字段填充处理器
     * @return
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }

    /**
     * 配置oracle主键生成器
     * @return
     */
//    @Bean
    public IKeyGenerator keyGenerator() {
        return new OracleKeyGenerator();
    }
}
