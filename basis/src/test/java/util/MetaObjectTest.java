package util;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;
import pwd.allen.entity.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 用于方便、优雅访问对象属性的对象，通过它可以简化代码、不需要try/catch各种reflect异常，同时它支持对JavaBean、Collection、Map三种类型对象的操作。
 *
 * @author 门那粒沙
 * @create 2020-06-08 21:06
 **/
public class MetaObjectTest {

    @Test
    public void one() {
        User user = new User();
        MetaObject metaObject = SystemMetaObject.forObject(user);
        System.out.println("get方法：" + Arrays.toString(metaObject.getGetterNames()));
        System.out.println("set方法：" + Arrays.toString(metaObject.getSetterNames()));
        System.out.println("age的type：" + metaObject.getGetterType("age"));

        System.out.println("-----赋值");
        metaObject.setValue("dept.deptName", "开发部");
        metaObject.setValue("dept.id", 123);

        //dept为null，会自动创建Department，但是如果是集合或者数组则创建不了
        System.out.println("user.dept:" + user.getDept());

        // 如果不给users初始化，后面会报错：The 'users' property of null is not a List or Array.
        user.getDept().setUsers(new ArrayList<>());
        // 如果users集合没有元素，后面会报错：java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        user.getDept().getUsers().add(new User());

        metaObject.setValue("dept.users[0].userName", "很多级的级联属性");

        System.out.println(user == metaObject.getOriginalObject());
        System.out.println(user);
    }

}