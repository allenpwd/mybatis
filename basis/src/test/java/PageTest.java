import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pwd.allen.dao.DepartmentDao;
import pwd.allen.dao.UserDao;
import pwd.allen.entity.Department;
import pwd.allen.entity.User;
import pwd.allen.util.ParamPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * mybatis是查出全部数据，然后再进行截取
 * 如果有引入PageInterceptor插件，则会在sql层面做分页，他有三种分页的实现方式：
 *  1、PageHelper.startPage；这里用了ThreadLocal
 *  2、使用rowBound做传参；默认不会执行count，可通过rowBoundsWithCount属性配置
 *  3、传参实现IPage接口
 * 这个分页插件支持自定义count sql语句，需要写一个id为 要查询的sqlId_COUNT
 *
 * 问题：分页的话，如果resultMap有内嵌的Collection，最好不要用join关联的方式，而是用select去单独查询
 *
 * @author 门那粒沙
 * @create 2020-11-08 15:38
 **/
public class PageTest {

    private static final Logger logger = LoggerFactory.getLogger(PageTest.class);

    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    DepartmentDao departmentDao = null;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        departmentDao = sqlSession.getMapper(DepartmentDao.class);
    }

    @After
    public void destroy() {
        sqlSession.close();
    }

    /**
     * 使用rowBound实现分页
     *
     */
    @Test
    public void rowBound() {
        Department param = new Department();
        param.setId(1);

        RowBounds rowBounds = new RowBounds(0, 1);
        // rowBounds不能传空，否则空指针；不想分页可以传RowBounds.DEFAULT
        List<Department> deptAndUsers = departmentDao.getDeptAndUsers(param, rowBounds);
        System.out.println(deptAndUsers);
    }

    /**
     * 分页方式：传参实现IPage接口
     */
    @Test
    public void useIPage() {
        Department dept = new Department();
        dept.setId(1);

        ParamPage<Department> paramPage = new ParamPage<>();
        paramPage.setParam(dept);
        paramPage.setPageNum(0);
        paramPage.setPageSize(1);

        List<Department> deptAndUsers = departmentDao.getDeptAndUsers2(paramPage);
        System.out.println(deptAndUsers);

        // 使用PageInfo获取分页信息
        PageInfo<Department> pageInfo = new PageInfo<>(deptAndUsers);
    }

    /**
     * 使用PageHelper实现分页
     * 使用方式：
     * 1.引入pageHelper
     * 2.配置PageInterceptor拦截器至全局配置文件
     *
     * 这个分页插件支持自定义count sql语句，需要写一个id为 要查询的sqlId_COUNT
     */
    @Test
    public void testPageHelper() {

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Page<Object> page = PageHelper.startPage(2, 1);
        List<User> users = userDao.getUsers(Collections.singletonMap("deptId", 1));
        logger.info("users={}", users);

        //分页数据可以从startPage返回的对象拿，也可以用PageInfo拿
        logger.info("page={}", page);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        logger.info("pageInfo={}", pageInfo);
    }
}
