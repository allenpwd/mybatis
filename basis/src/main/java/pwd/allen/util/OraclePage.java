package pwd.allen.util;

import lombok.Data;
import pwd.allen.entity.User;

import java.util.List;

/**
 * @author 门那粒沙
 * @create 2019-07-07 14:34
 **/
@Data
public class OraclePage {
    private int start;
    private int end;
    private int total;
    private List<User> data;
}
