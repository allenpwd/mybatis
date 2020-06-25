package pwd.allen.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;

import java.util.List;

/**
 * @author 门那粒沙
 * @create 2020-06-25 21:32
 **/
public class MySqlInjector extends AbstractSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return null;
    }
}
