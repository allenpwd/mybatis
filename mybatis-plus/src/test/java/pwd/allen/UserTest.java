package pwd.allen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.util.Date;
import java.util.List;

/**
 * @author 门那粒沙
 * @create 2020-06-16 22:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }


    @Test
    public void testSave() {
        User user = new User();
        user.setId(123);
        user.setUserName("mybatis-plus");
        user.setMsg("mybatis-plus".getBytes());
        user.setCreateAt(new Date());
        user.setAge(11);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
}
