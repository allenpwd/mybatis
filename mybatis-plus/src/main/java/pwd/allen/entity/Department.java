package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author pwd
 * @create 2018-07-25 9:39
 **/
@KeySequence(value = "seq_dept")  //指定主键序列，IdType策略需要指定为INPUT
@TableName(value = "db_dept", excludeProperty = {"users"})
@Data
public class Department implements Serializable {
    @TableId(type = IdType.INPUT)
    private Integer id;
    private String deptName;
    private List<User> users;
}

