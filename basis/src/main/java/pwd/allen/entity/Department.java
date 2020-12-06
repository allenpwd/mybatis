package pwd.allen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author pwd
 * @create 2018-07-25 9:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {
    @NonNull
    private String deptName;
    private List<User> users;
}

