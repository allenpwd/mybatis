import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.dao.DepartmentDao;
import pwd.allen.entity.Department;
import pwd.allen.util.ParamPage;

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

    /**
     * 使用rowBound实现分页
     *
     * mybatis是查出全部数据，然后再进行截取
     * 如果有引入PageInterceptor插件，则会在sql层面做分页，他有三种分页的实现方式：
     *  1、PageHelper.startPage；这里用了ThreadLocal
     *  2、使用rowBound做传参；默认不会执行count，可通过rowBoundsWithCount属性配置
     *  3、传参实现IPage接口
     *
     * 问题：分页的话，如果resultMap有内嵌的Collection，最好不要用join关联的方式，而是用select去单独查询
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

        // rowBounds不能传空，否则空指针；不想分页可以传RowBounds.DEFAULT
        List<Department> deptAndUsers = departmentDao.getDeptAndUsers2(paramPage);
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
