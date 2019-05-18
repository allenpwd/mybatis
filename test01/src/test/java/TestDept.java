import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.dao.DepartmentDao;
import pwd.allen.entity.Department;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author pwd
 * @create 2018-07-25 18:37
 **/
public class TestDept {

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
    public void test01() {
        DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

        Department department = null;
        List<Department> list = null;

        /*list = departmentDao.getDeptAndUsers(null);
        System.out.println(list);*/
        //System.out.println(department.getUsers());

        department = departmentDao.getDeptAndUsersStep(2);
        System.out.println(department);
        System.out.println(department.getUsers());
    }

    @Test
    public void addDepts() {
        DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
        List<Department> list = new ArrayList<Department>();
        Department dept = new Department();
        dept.setId(1);
        dept.setDeptName("综合部");
        list.add(dept);
        dept = new Department();
        dept.setId(2);
        dept.setDeptName("人事部");
        list.add(dept);
        int rel = departmentDao.addDepts(list);
        System.out.println("rel:" + rel);
        sqlSession.commit();
    }
}
