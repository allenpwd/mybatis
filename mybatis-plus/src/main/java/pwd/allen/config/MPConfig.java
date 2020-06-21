package pwd.allen.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new PaginationInterceptor();
    }
}
