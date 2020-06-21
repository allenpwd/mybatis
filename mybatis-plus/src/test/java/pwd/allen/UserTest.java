package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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

    /**
     * 根据id查询
     */
    @Test
    public void selectById() {
        User user = userMapper.selectById(1);
        System.out.println(user);

        System.out.println(userMapper.selectBatchIds(Arrays.asList(1, 2, 20)));
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
    public void insert() {
        User user = new User();
        user.setId(100);
        user.setUserName("mybatis-plus创建");
        user.setAge(22);
        user.setDeptId(1);
        user.setCreateAt(new Date());
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(20);
        user.setUserName("菠萝蜜");
//        user.setAge(22);
        user.setDeptId(1);
        user.setCreateAt(new Date());
        // 会根据实体类的每个属性进行非空判断，只有非空的才会出现在sql语句中
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    @Test
    public void selectList() {
        System.out.println(userMapper.selectByMap(Collections.singletonMap("user_name", "门那粒沙")));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "门");
        System.out.println(userMapper.selectList(queryWrapper));
    }

    /**
     * 分页查询
     */
    @Test
    public void selectPage() {
        Page<User> userPage = new Page<>(1, 2, false);
        userMapper.selectPage(userPage, null);
        System.out.println(userPage.getRecords());
    }

    @Test
    public void wrapper() {
    }
}
