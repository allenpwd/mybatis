package pwd.allen.util;

import com.github.pagehelper.IPage;
import lombok.Data;

/**
 * @author 门那粒沙
 * @create 2020-06-15 23:37
 **/
@Data
public class ParamPage<T> implements IPage {

    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;
    private T param;
}
