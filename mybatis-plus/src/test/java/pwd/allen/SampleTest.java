package pwd.allen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.util.List;

/**
 * @author 门那粒沙
 * @create 2020-06-16 22:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
