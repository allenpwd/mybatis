package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.Department;
import pwd.allen.entity.User;
import pwd.allen.handler.MyMetaObjectHandler;
import pwd.allen.mapper.DeptMapper;
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
public class DeptTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private DeptMapper deptMapper;

    @Before
    public void init() throws IOException {
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));

        MybatisConfiguration configuration = (MybatisConfiguration) sqlSessionFactory.getConfiguration();
        // 设置主键自动生成为oracle序列
        GlobalConfig globalConfig = configuration.getGlobalConfig();
        globalConfig.getDbConfig().setKeyGenerator(new OracleKeyGenerator());

        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());

        sqlSession = sqlSessionFactory.openSession();
        deptMapper = sqlSession.getMapper(DeptMapper.class);
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
        Department department = deptMapper.selectById(1);
        System.out.println(department);

        System.out.println(deptMapper.selectBatchIds(Arrays.asList(1, 2, 20)));
    }

    @Test
    public void insert() {
        Department department = new Department();
//        department.setId(101);
        department.setDeptName("临时部");
        int insert = deptMapper.insert(department);
        System.out.println(insert);
    }
}
