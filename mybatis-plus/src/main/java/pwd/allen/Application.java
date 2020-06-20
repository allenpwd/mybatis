package pwd.allen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 门那粒沙
 * @create 2020-06-16 22:17
 **/
@MapperScan("pwd.allen.mapper")
@SpringBootApplication
//@EnableTransactionManagement  //开启事务
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
