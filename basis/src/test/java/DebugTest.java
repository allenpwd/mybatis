import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.junit.Test;
import pwd.allen.entity.User;

import java.util.Arrays;
import java.util.HashMap;

/**
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
        paramMap.put("user", user);
        paramMap.put("list", Arrays.asList("A", "B"));

        System.out.println(evaluator.evaluateBoolean("user.userName != null && user.userName.length() == 3", paramMap));
        System.out.println(evaluator.evaluateBoolean("list != null and list.size() > 0", paramMap));
        System.out.println(evaluator.evaluateBoolean("user.userName == 'abc'", paramMap));      //旧版字符串要用双引号
        System.out.println(Ognl.getValue("user.userName", paramMap));

        System.out.println(Ognl.parseExpression("list != null && list.size() > 0"));
    }
}
