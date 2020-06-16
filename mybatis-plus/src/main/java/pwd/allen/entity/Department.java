package pwd.allen.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author pwd
 * @create 2018-07-25 9:39
 **/
@Data
public class Department implements Serializable {
    private Integer id;
    private String deptName;
    private List<User> users;
}

