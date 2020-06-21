package pwd.allen;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;
import pwd.allen.service.IUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 门那粒沙
 * @create 2020-06-16 22:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SpringTest {

    @Autowired
    private IUserService userService;

    /**
     * 通过mapper.xml配置
     */
    @Test
    public void mySelect() {
        User userWithDept = userService.getUserWithDept(1);
        System.out.println(userWithDept);
    }

    @Test
    public void testGetList() {
        //方法一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", 1);
        queryWrapper.like("user_name", "mybatis-plus");
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);
        //方法二
        userList = userService.query()
                .like("user_name", "mybatis-plus").list();
        //方法三
        userList = userService.lambdaQuery()
                .like(User::getUserName, "mybatis-plus").list();

        // 使用转换函数，TODO 为何是整型的，怎么拿到其他属性
        List<String> idList = userService.listObjs(queryWrapper, o -> {
            return "id=" + o;
        });
        System.out.println(idList);
    }

    @Test
    public void page() {
        Page<User> page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);
        page.setSearchCount(false);
        Page<User> userPage = userService.page(page);
        System.out.println(userPage.getRecords());
    }


    /**
     * 批量保存
     */
    @Test
    @Transactional
    public void testSaveOrUpdate() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(12345 + i);
            user.setUserName("mybatis-plus创建" + i);
            user.setAge(22);
            user.setDeptId(1);
            user.setCreateAt(new Date());
            userList.add(user);
        }
        boolean b = userService.saveOrUpdateBatch(userList, 2);
        System.out.println(b);
    }
}
