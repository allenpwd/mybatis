package pwd.allen;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.junit.Test;
import pwd.allen.entity.User;

/**
 * @author 门那粒沙
 * @create 2020-06-20 21:19
 **/
public class OtherTest {

    @Test
    public void IdGenerator() {
        DefaultIdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();

        User user = new User();
        Long id = identifierGenerator.nextId(user);
        System.out.println(id);
    }
}
