package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.User;
import pwd.allen.handler.MyMetaObjectHandler;
import pwd.allen.mapper.UserMapper;
import pwd.allen.util.MyIdGenerator;

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

        MybatisConfiguration configuration = (MybatisConfiguration) sqlSessionFactory.getConfiguration();
        // 自定义ID生成器
        GlobalConfig globalConfig = configuration.getGlobalConfig();
        globalConfig.setIdentifierGenerator(new MyIdGenerator());

        // 设置全局逻辑删除值，没有效果，因为mapperStatement已经解析完了
//        configuration.getGlobalConfig().getDbConfig().setLogicDeleteValue("1");
//        configuration.getGlobalConfig().getDbConfig().setLogicNotDeleteValue(null);

        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());

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
//        user.setId(100);
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
     * 分页返回的对象与传入的对象是同一个
     */
    @Test
    public void selectPage() {
        Page<User> userPage = new Page<>(1, 2, false);
        userMapper.selectPage(userPage, null);
        System.out.println(userPage.getRecords());


        // 自定义的分页
        userMapper.selectByPage(userPage, null);
        System.out.println(userPage);
    }

    /**
     * 分页查询
     * 分页返回的对象与传入的对象是同一个
     */
    @Test
    public void selectPageTwo() {
        Page<User> userPage = new Page<>(1, 2, false);
        userMapper.selectPage(userPage, null);
        System.out.println(userPage.getRecords());


        // 自定义的分页
        userMapper.selectByPage(userPage, null);
        System.out.println(userPage);
    }

    /**
     * 逻辑删除
     * sql:UPDATE db_user SET deleted=1 WHERE id=? AND deleted=0
     */
    @Test
    public void logicDelete() {
        System.out.println(userMapper.deleteById(100));
    }
}
