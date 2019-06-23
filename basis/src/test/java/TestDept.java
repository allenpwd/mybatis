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

//        list = departmentDao.getDeptAndUsers(null);
//        System.out.println(list);

        //collection标签关联查询users
        //todo collection设置了fetchType=lazy但还是没有懒加载
//        department = departmentDao.getDeptAndUsersStep(2);
//        System.out.println(department.getId());
//        System.out.println(department.getUsers());

        //测试鉴别器 id=1时关联出users；id=2时将id赋值给deptName
        department = departmentDao.getDeptAndUsersStep2(1);
        System.out.println(department.getUsers());
        department = departmentDao.getDeptAndUsersStep2(2);
        System.out.println(department);
        department = departmentDao.getDeptAndUsersStep2(2);
        department = departmentDao.getDeptAndUsersStep2(2);
        department = departmentDao.getDeptAndUsersStep2(2);
        department = departmentDao.getDeptAndUsersStep2(2);
        department = departmentDao.getDeptAndUsersStep2(2);
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
