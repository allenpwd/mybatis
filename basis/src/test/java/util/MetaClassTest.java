package util;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.junit.Test;
import pwd.allen.entity.User;

import java.util.Arrays;

/**
 * 用于获取类相关的信息
 *
 * @author 门那粒沙
 * @create 2020-06-08 21:58
 **/
public class MetaClassTest {

    @Test
    public void one() {
        MetaClass metaClass = MetaClass.forClass(User.class, new DefaultReflectorFactory());
        System.out.println("获取所有有getter方法的属性名：" + Arrays.toString(metaClass.getGetterNames()));

        System.out.println("是否有无参构造函数：" + metaClass.hasDefaultConstructor());

        System.out.println("age属性是否有getter方法：" + metaClass.hasGetter("age"));

        //获取getter方法
        Invoker invoker = metaClass.getGetInvoker("age");
        System.out.println(invoker.getType());
        System.out.println(metaClass.getSetterType("age"));
    }
}
