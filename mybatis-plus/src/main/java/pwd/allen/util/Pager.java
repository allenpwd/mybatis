package pwd.allen.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Map;

/**
 * @author lenovo
 * @create 2020-09-09 9:53
 **/
@Data
public class Pager<T> extends Page<T> {

    private Map<String, Object> parameters;
}
