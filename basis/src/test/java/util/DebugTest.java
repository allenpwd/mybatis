package util;

import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.junit.Test;
import pwd.allen.entity.Department;
import pwd.allen.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * test属性里写的是ognl表达式，支持访问静态方法（OGNL在调用静态方法属性时，需要类的名称为全称。而在Java.lang.Math类的默认方法则可以不填类的全名称）
 * @author lenovo
 * @create 2020-06-15 9:21
 **/
public class DebugTest {

    /**
     * 测试 mybatis动态标签里的test属性
     */
    @Test
    public void expressEvaluator() throws OgnlException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();

        HashMap<Object, Object> paramMap = new HashMap<>();

        User user = new User();
        user.setUserName("abc");
        user.setAge(0);
        paramMap.put("user", user);
        paramMap.put("list", Arrays.asList(new Department("dept1", null), new Department("dept2", null)));
        HashMap<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        paramMap.put("map", map);

        evaluator.evaluateBoolean("@pwd.allen.util.MyUtil@isNotEmply(list)", paramMap); // 调用静态方法
        evaluator.evaluateBoolean("@pwd.allen.util.MyUtil@ZERO < user.age", paramMap); // 调用静态属性

        System.out.println(evaluator.evaluateBoolean("user.userName != null && user.userName.length() == 3", paramMap));
        System.out.println(evaluator.evaluateBoolean("user.userName == 'abc'", paramMap));      //旧版字符串要用双引号
        System.out.println(Ognl.getValue("user.userName", paramMap));
        // 如果整数类型是0，会被转成NULL
        System.out.println(evaluator.evaluateBoolean("user.age != null and user.age != ''", paramMap));

        System.out.println(evaluator.evaluateBoolean("list != null and list.size() > 0", paramMap));
        System.out.println(evaluator.evaluateIterable("list.{deptName}", paramMap)); // 获取list的deptName属性集合
        System.out.println(evaluator.evaluateIterable("map.keys", paramMap));
        System.out.println(evaluator.evaluateIterable("map.values", paramMap));
        System.out.println(Ognl.getValue("map.key1", paramMap));
        System.out.println(Ognl.getValue("map['key1']", paramMap));

        // ExpressionEvaluator其实底层是直接调用Ognl的
        System.out.println(Ognl.parseExpression("list != null && list.size() > 0"));


    }
}
