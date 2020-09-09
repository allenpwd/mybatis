package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import pwd.allen.enums.StatusEnum;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-16 23:27
 **/
@TableName("db_user")
@Data
public class User extends Model<User> {

    /**
     * 标注主键
     *  id:字段名
     *  type:主键类型
     *      ASSIGN_ID：分配ID(主键类型为Number(Long和Integer)或String)(since 3.3.0),使用接口IdentifierGenerator的方法nextId
     *      AUTO：数据库ID自增，好像即使手动设置id也不会传
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;
    private String userName;
    @TableField(updateStrategy = FieldStrategy.IGNORED) //为空时也更新到数据库
    private Integer age;
    private StatusEnum status;
    private Integer deptId;
    private Date createAt;

    /**
     * 字段注解(非主键)
     *  value：数据库字段名
     */
    @TableField(exist = false)
    private Department dept;
    @TableField(fill = FieldFill.INSERT_UPDATE) //插入或者更新都进行填充处理，需借助MetaObjectHandler，判断优先级比 {@link FieldStrategy} 高
    private byte[] msg;

    /**
     * 逻辑删除字段，null未删除 1已删除
     */
//    @TableLogic(value = "null", delval = "1")
//    private int deleted;
}
