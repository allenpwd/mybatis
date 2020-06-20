package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.io.IOException;
import java.util.Date;

/**
 * 测试UserMapper
 *
 * @author 门那粒沙
 * @create 2020-06-20 14:50
 **/
public class UserTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void init() throws IOException {
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void destroy() {
        sqlSession.close();
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    /**
     * 通过mapper.xml定义的方法
     */
    @Test
    public void getUserWithDept() {
        User user = userMapper.getUserWithDept(1);
        System.out.println(user);
    }

    @Test
    public void save() {
        User user = new User();
        user.setId(100);
        user.setUserName("mybatis-plus创建");
        user.setAge(22);
        user.setDeptId(1);
        user.setCreateAt(new Date());
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
}
