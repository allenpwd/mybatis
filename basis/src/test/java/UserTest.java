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
import pwd.allen.dao.UserDao;
import pwd.allen.dao.UserDaoAnnotation;
import pwd.allen.entity.Department;
import pwd.allen.entity.User;
import pwd.allen.util.MyPage;
import pwd.allen.util.StatusEnum;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author pwd
 * @create 2018-07-22 14:31
 **/
@Slf4j
public class UserTest {

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
//        list_param.add("门那粒沙");
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
//        user.setId(5);
        user.setAge(21);
        user.setUserName("史珍香");
        user.setStatus(StatusEnum.ENABLED);
        user.setCreateAt(new Date());
        Department dept = new Department();
        dept.setId(1);
        user.setDept(dept);

        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        int rel = userDao.insertUser(user);
        int rel = userDao.addUser(user);

        System.out.println("rel:" + rel);
        System.out.println("user:" + user);

//        sqlSession.commit();
    }

    /**
     * 使用[set autocommit=0;SELECT * FROM DB_USER where id=1 for update;]锁住指定行
     * 然后执行，发现timeout有效果，5秒后抛异常com.mysql.jdbc.exceptions.MySQLTimeoutException: Statement cancelled due to timeout or client request
     * 使用show processlist查看连接，能看到这条连接state=updating，卡了5s后断开了
     *
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    @Test
    public void update() throws UnsupportedEncodingException, InterruptedException {
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        User user = new User();
        user.setId(1);
        user.setAge(21);
        user.setUserName("门那粒沙");
        user.setCreateAt(new Date());
        user.setMsg("你好".getBytes("GBK"));

        int rel = userDao.update(user);

        System.out.println("rel:" + rel);
        System.out.println("user:" + user);

//        sqlSession.commit();
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
        log.info("users={}", users);

        //分页数据可以从startPage返回的对象拿，也可以用PageInfo拿
        log.info("page={}", page);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        log.info("pageInfo={}", pageInfo);
    }

    /**
     * 批量插入
     *
     * 设置为ExecutorType.BATCH后返回值不是成功插入的记录数，因为不是调用addUser就立即执行的
     */
    @Test
    public void testInsertBatch() {

        //批量 预编译一次、sql请求一次、设置参数10000次
        //如果是非批量模式，则每次insert都发请求一次
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        long mills = System.currentTimeMillis();

        List<User> list = getData(1480, 5000);
        for (User user : list) {
            Integer rel = userDao.insertOrUpdateUser(user);
            System.out.println("rel=" + rel);
        }

        sqlSession.commit();

        log.info("耗时{}", System.currentTimeMillis() - mills);
    }


    /**
     * 批量更新
     */
    @Test
    public void updateBatch() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        long mills = System.currentTimeMillis();

        List<User> list = getData(0, 5000);

        int per = 1000;
        int begin = 0;
        while (begin < list.size()) {
            int end = (begin + per) > list.size() ? list.size() : (begin + per);
            userDao.updateBatch(list.subList(begin, end));// 耗时1857 耗时1931
//            userDao.updateBatch2(list.subList(begin, end));// 耗时1429 耗时1395
            begin = end;
        }

        sqlSession.commit();

        log.info("耗时{}", System.currentTimeMillis() - mills);
    }

    /**
     * 插入或者更新
     */
    @Test
    public void testInsertOrUpdate() throws SQLException {

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        long mills = System.currentTimeMillis();

        List<User> list = getData(0, 500);
        userDao.insertOrUpdateUserBatsh(list);

        sqlSession.commit();

        log.info("耗时{}", System.currentTimeMillis() - mills);
    }

    /**
     * 调用存储过程
     */
    @Test
    public void testMysqlProcedure() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        MyPage page = new MyPage();
        page.setPageNum(1);
        page.setPageSize(1);
        List<User> dataList = userDao.getPageByProcedure4Mysql(page);
        log.info("total={}, data={}", page.getTotal(), dataList);
    }

    /**
     * 调用存储过程，利用oracle游标返回分页列表数据
     */
    @Test
    public void testOracleProcedure() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        MyPage page = new MyPage();
        page.setPageNum(1);
        page.setPageSize(1);
        userDao.getPageByProcedure4Oracle(page);
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

    List<User> getData(int start, int num) {
        Random random = new Random();
        List<User> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setId(start + i);
            user.setCreateAt(new Date());
            user.setAge(10 + random.nextInt(25));
            user.setStatus(StatusEnum.ENABLED);
            user.setMsg(UUID.randomUUID().toString().getBytes());
            user.setUserName(UUID.randomUUID().toString().substring(0, 32));
            list.add(user);
        }
        return list;
    }

}
