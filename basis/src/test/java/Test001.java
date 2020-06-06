import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pwd.allen.dao.UserDao;
import pwd.allen.dao.UserDaoAnnotation;
import pwd.allen.entity.Department;
import pwd.allen.entity.User;
import pwd.allen.util.OraclePage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;

/**
 * @author pwd
 * @create 2018-07-22 14:31
 **/
@Slf4j
public class Test001 {

    private SqlSessionFactory sqlSessionFactory = null;
    private SqlSession sqlSession = null;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //有其他多态方法可以指定环境和属性
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //sqlSession代表和数据库的一次会话，用完需关闭，和connection一样非线程安全，每次使用都应该去获取新的对象
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void destroy() {
        sqlSession.close();
    }

    /**
     * 根据userId查询User
     */
    @Test
    public void selectOne() {
        User user = null;

        //旧版本的查询方法
        //user = sqlSession.selectOne("pwd.allen.dao.UserDao.selectUser", 1);

        //新版本 根据接口生成一个代理对象，将mapper和接口绑定，接口类全名要和mapper namespace对应
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //最终会去调用 sqlSession.selectOne("pwd.allen.dao.UserDao.selectUser", params)
        //params类型为ParamMap，映射的值为 {id=1,param1=1}
        user = userDao.getById(1);
        log.info("user={}", user);

    }

    @Test
    public void selectXml() {

        User user = null;
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        List<Object> list_param = new ArrayList<Object>();
//        list_param.add(1);
//        list_param.add("潘伟丹");
//        user = userDao.getByList(list_param);
//        System.out.println(user);

//        user = userDao.getByIdAndUserName(1, "潘伟丹");
//        System.out.println(user);

        Map<String, Object> map_param = new HashMap<String, Object>();
//        map_param.put("type", 2);
//        map_param.put("id", 1);
//        map_param.put("userName", "潘伟丹");
//        user = userDao.selectByMap(map_param);
//        System.out.println(user);

        //根据userID查询，返回map，返回值为null的字段不会返回，除非callSettersOnNulls属性设置为true
//        Map<String, Object> map = userDao.getByIdReturnMap(5);
//        System.out.println(map);

//        List<User> list = userDao.getsByUserNameLike("%%");
//        System.out.println(list);

//        Map<Object, User> map = userDao.getsByAgeLtReturnMap(50);
//        System.out.println(map);

        //association 直接关联出dept信息
        user = userDao.getUserAndDept(1);
        log.info("user={}", user);
        log.info("dept={}", user.getDept());

        //association 分布关联出dept信息，可设置懒加载（lazyLoadingEnabled=true 和 aggressiveLazyLoading=false）
//        user = userDao.getStep(1);
//        System.out.println(user.getUserName());
//        System.out.println(user.getDept());

//        List<User> users = userDao.getUsersByDeptId(1);
//        System.out.println(users);

    }

    @Test
    public void insertXml() {
        User user = new User();
        //user.setId(5);
        user.setAge(21);
        user.setUserName("黄鹏嘉");
        user.setStatus(2);
        user.setCreateAt(new Date());
        Department dept = new Department();
        dept.setId(1);
        user.setDept(dept);

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //int rel = userDao.insertUser(user);
        int rel = userDao.addUser(user);

        System.out.println("rel:" + rel);
        System.out.println("user:" + user);

        sqlSession.commit();
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(2);
        user.setAge(21);
        user.setUserName("黄鹏嘉1");
        user.setCreateAt(new Date());
        try {
            user.setMsg("你好".getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
        }

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int rel = userDao.update(user);

        System.out.println("rel:" + rel);
        System.out.println("user:" + user);

        sqlSession.commit();
    }

    @Test
    public void testAnnotation() {

        //旧版本的查询方法
        //User user = sqlSession.selectOne("pwd.allen.dao.UserDaoAnnotation.selectUser", 1);

        UserDaoAnnotation userDaoAnnotation = sqlSession.getMapper(UserDaoAnnotation.class);
        User user = userDaoAnnotation.selectUser(1);

        log.info("user={}", user);
    }

    /**
     * 二级缓存 需要设置cacheEnabled=true并在mapper里配置cache标签，实体类需要能序列化
     */
    @Test
    public void testSecondCache() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.getById(1);
        sqlSession.close();

        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        //第二次 缓存命中，日志打印Cache Hit Ratio [pwd.allen.dao.UserDao]: 0.5
        user = sqlSession1.getMapper(UserDao.class).getById(1);
    }

    /**
     * 使用SystemMetaObject工具操作对象属性
     */
    @Test
    public void testMetaObject() {
        User user = new User();
        MetaObject metaObject = SystemMetaObject.forObject(user);
        metaObject.setValue("userName", "abc");
        metaObject.setValue("dept.deptName", "部门名称");
        System.out.println(metaObject.getValue("dept.deptName"));
    }

    /**
     * 使用PageHelper实现分页
     * 使用方式：
     * 1.引入pageHelper
     * 2.配置PageInterceptor拦截器至全局配置文件
     */
    @Test
    public void testPageHelper() {

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Page<Object> page = PageHelper.startPage(1, 2);
        List<User> users = userDao.getUsers(null);
        log.info("users={}", users);

        //分页数据可以从startPage返回的对象拿，也可以用PageInfo拿
        log.info("page={}", page);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        log.info("pageInfo={}", pageInfo);
    }

    /**
     * 批量插入
     */
    @Test
    public void testInsertBatch() {

        //批量 预编译一次、sql请求一次、设置参数10000次
        //如果是非批量模式，则每次insert都发请求一次
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        long mills = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setCreateAt(new Date());
            user.setAge(i);
            user.setUserName(UUID.randomUUID().toString().substring(0, 10));
            userDao.insertUser(user);
        }
        log.info("耗时{}", System.currentTimeMillis() - mills);
    }

    /**
     * 插入或者更新
     */
    @Test
    public void testInsertOrUpdate() throws SQLException {

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        long mills = System.currentTimeMillis();

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(20 + i);
            user.setCreateAt(new Date());
            user.setAge(26);
            user.setUserName(UUID.randomUUID().toString().substring(0, 10));
            list.add(user);
        }
        list.add(list.get(0));
        list.add(list.get(0));
        userDao.insertOrUpdateUserBatsh(list);

//        sqlSession.commit();

        log.info("耗时{}", System.currentTimeMillis() - mills);

    }

    /**
     * 调用存储过程，利用oracle游标返回分页列表数据
     */
    @Test
    public void testOracleProcedure() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        OraclePage page = new OraclePage();
        page.setStart(1);
        page.setEnd(2);
        userDao.getPageByProcedure(page);
        log.info("total={}, data={}", page.getTotal(), page.getData());
    }

    @Test
    public void pwdTest() throws SQLException {
        Connection connection = sqlSession.getConnection();
        String sql = "select * from db_user where user_NAME=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "潘伟丹");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getString("USER_NAME"));
        }
    }

}
