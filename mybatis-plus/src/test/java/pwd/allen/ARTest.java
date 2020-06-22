package pwd.allen;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import pwd.allen.entity.User;

import java.io.IOException;
import java.util.Date;

/**
 * activeRecord模式：实体类继承Model类，并通过注解与数据库的表名进行关联，这样就可以通过实体类直接进行表的简单增删改查操作
 *  类似语法糖，本质是调用mybatis相关方法
 * @author 门那粒沙
 * @create 2020-06-21 20:31
 **/
public class ARTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
    }

    @Test
    public void insertAndUpdate() {
        User userAR = new User();
        userAR.setId(250);
        userAR.setAge(22);
        userAR.setUserName("AR");
        userAR.setCreateAt(new Date());
        userAR.setDeptId(1);

        boolean insert = userAR.insert();
        System.out.println(insert);

        userAR.setUserName(userAR.getUserName() + "修改");
        System.out.println(userAR.updateById());
    }

    @Test
    public void select() {
        User userAR = new User();
        User user = userAR.selectById(1);
        System.out.println(user);
         System.out.println(userAR.selectList(new LambdaQueryWrapper<User>().like(User::getUserName, "门那粒沙")));
    }

    @Test
    public void page() {
        User userAR = new User();
        Page<User> page = userAR.selectPage(new Page<User>(1, 2), null);
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
    }
}
