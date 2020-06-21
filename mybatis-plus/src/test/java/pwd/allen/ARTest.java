package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.User;

import java.io.IOException;
import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-21 20:31
 **/
public class ARTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
    }

    @Test
    public void insert() {
        User userAR = new User();
        userAR.setAge(22);
        userAR.setUserName("AR");
        userAR.setCreateAt(new Date());
        userAR.setDeptId(1);

        boolean insert = userAR.insert();
        System.out.println(insert);
    }
}
