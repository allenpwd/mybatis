package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pwd.allen.enums.StatusEnum;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-16 23:27
 **/
@TableName("db_user")
@Data
public class User {

    /**
     * 标注主键
     *  id:字段名
     *  type:主键类型
     *      ASSIGN_ID：分配ID(主键类型为Number(Long和Integer)或String)(since 3.3.0),使用接口IdentifierGenerator的方法nextId
     *      AUTO：数据库ID自增，好像即使手动设置id也不会传
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private int age;
    private StatusEnum status;
    private Integer deptId;
    private Date createAt;

    /**
     * 字段注解(非主键)
     *  value：数据库字段名
     */
    @TableField(exist = false)
    private Department dept;
    private byte[] msg;
}
