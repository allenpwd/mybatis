package pwd.allen.entity;

import lombok.Data;
import lombok.ToString;
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
@ToString(callSuper = true)
public class User extends BaseEntity {
    private String userName;
    private int age;
    private StatusEnum status;
    private Department dept;
    private byte[] msg;
}
