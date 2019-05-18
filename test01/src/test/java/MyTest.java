import org.apache.ibatis.io.Resources;
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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author pwd
 * @create 2018-07-22 14:31
 **/
public class MyTest {

    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;

    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void destroy() {
        sqlSession.close();
    }

    @Test
    public void selectXml() {

        //region Description
        //旧版本的查询方法
        //User user = sqlSession.selectOne("pwd.allen.dao.UserDao.selectUser", 1);
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<Object> list_param = new ArrayList<Object>();
        User user = null;

//        user = userDao.getById(1);
//        System.out.println(user);

        /*list_param.add(1);
        list_param.add("潘伟丹");
        user = userDao.getByList(list_param);
        System.out.println(user);*/

        user = userDao.getByIdAndUserName(1, "潘伟丹");
        System.out.println(user);

        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("userName", "潘伟丹");
        user = userDao.selectByMap(map);
        System.out.println(user);*/

        /*Map<String, Object> map = userDao.getByIdReturnMap(1);
        System.out.println(map);*/

        //List<User> list = userDao.getsByUserNameLike("%%");
        //System.out.println(list);

        /*Map<Object, User> map = userDao.getsByAgeLtReturnMap(50);
        System.out.println(map);

        user = userDao.getUserAndDept(1);
        System.out.println(user);
        System.out.println(user.getDept());*/

        /*user = userDao.getStep(1);
        System.out.println(user.getUserName());
        System.out.println(user.getDept());*/
        //endregion

        /*List<User> users = userDao.getUsersByDeptId(1);
        System.out.println(users);*/
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
    public void testAnnotation() {

        //旧版本的查询方法
        //User user = sqlSession.selectOne("pwd.allen.dao.UserDao.selectUser", 1);

        UserDaoAnnotation userDaoAnnotation = sqlSession.getMapper(UserDaoAnnotation.class);
        User user = userDaoAnnotation.selectUser(1);

        System.out.println(user);
    }

    @Test
    public void test2() throws SQLException {
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
