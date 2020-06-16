package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-16 23:27
 **/
@TableName("db_user")
@Data
public class User {
    private Integer id;
    private String userName;
    private int age;
    private int status;
    private Date createAt;

    @TableField(exist = false)
    private Department dept;
    private byte[] msg;
}
