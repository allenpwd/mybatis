import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DeptTest {

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

    @Test
    public void rowBound() {
        List<Department> deptAndUsers = departmentDao.getDeptAndUsers(null);
        System.out.println(deptAndUsers);
    }


    /**
     * 关联查询
     *
     * 遇到的问题：collection设置了fetchType=lazy但还是没有懒加载
     *  原因：可能是debug导致lazyLoadTriggerMethods指定的方法被触发；不用debug的话就正常了
     */
    @Test
    public void collectionTest() throws InterruptedException {
        //一次性关联查询出来
//        List<Department> list = departmentDao.getDeptAndUsers(null);
//        System.out.println(list);

        //collection标签关联查询users
        Department department = departmentDao.getDeptAndUsersStep(1);
        System.out.println("department.id：" + department.getId());
        System.out.println("懒加载还没有被触发");

        System.out.println(department.getUsers());
    }

    /**
     * 测试鉴别器 id=1时关联出users；id=2时将id赋值给deptName
     */
    @Test
    public void test02() {
        Department department = departmentDao.getDeptAndUsersStep2(1);
        System.out.println(department);
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
