import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pwd.allen.mybatis.dao.UserDao;
import pwd.allen.mybatis.entity.User;
import pwd.allen.mybatis.service.UserService;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-25 16:17
 **/
@ContextConfiguration("classpath:/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Test
    public void test() {
        User user = userService.getUser(1);
        System.out.println(user);
    }

}
