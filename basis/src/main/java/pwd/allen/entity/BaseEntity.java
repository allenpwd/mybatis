package pwd.allen.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-10-13 22:48
 **/
@Data
public class BaseEntity implements Serializable {
    private Integer id;
    private Date createAt;
}
