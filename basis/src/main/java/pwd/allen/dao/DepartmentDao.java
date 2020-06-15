package pwd.allen.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import pwd.allen.entity.Department;
import pwd.allen.util.ParamPage;

import java.util.List;

/**
 * @author pwd
 * @create 2018-07-25 10:55
 **/
public interface DepartmentDao {
    public Department getById(Integer id);

    public List<Department> getDeptAndUsers(Department dept, RowBounds rowBounds);

    public List<Department> getDeptAndUsers2(ParamPage<Department> paramPage);

    public Department getDeptAndUsersStep(Integer id);
    public Department getDeptAndUsersStep2(Integer id);

    public Integer addDepts(@Param("depts") List<Department> depts);

}
