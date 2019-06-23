package pwd.allen.dao;

import org.apache.ibatis.annotations.Param;
import pwd.allen.entity.Department;

import java.util.List;

/**
 * @author pwd
 * @create 2018-07-25 10:55
 **/
public interface DepartmentDao {
    public Department getById(Integer id);

    public List<Department> getDeptAndUsers(Integer id);

    public Department getDeptAndUsersStep(Integer id);
    public Department getDeptAndUsersStep2(Integer id);

    public Integer addDepts(@Param("depts") List<Department> depts);

}
