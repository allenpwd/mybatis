package pwd.allen.util;

import com.github.pagehelper.util.StringUtil;

import java.util.Collection;

/**
 * @author lenovo
 * @create 2020-12-04 15:28
 **/
public class MyUtil {

    public static final Integer ZERO = 0;

    public static boolean isNotEmply(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            return StringUtil.isNotEmpty(String.class.cast(o));
        }
        if (o instanceof Collection) {
            return Collection.class.cast(o).size() > 0 ? true : false;
        }
        if (o instanceof Object[]) {
            return Object[].class.cast(o).length > 0 ? true : false;
        }
        return true;
    }
}
