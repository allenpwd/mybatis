package pwd.allen.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import pwd.allen.util.StatusEnum;

import java.io.Serializable;
import java.util.Date;

@Alias("user")
/**
 * @author pwd
 * @create ${Year}-07-22 14:22
 **/
@Data
public class User implements Serializable {
    private Integer id;
    private String userName;
    private int age;
    private StatusEnum status;
    private Date createAt;
    private Department dept;
    private byte[] msg;
}
