package pwd.allen.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hamcrest.core.Is;
import pwd.allen.entity.User;
import pwd.allen.util.OraclePage;

import java.util.List;
import java.util.Map;

/**
 * sql可以配置在xml，也可以在方法上用注解配置，但是不能重名
 *
 * @author pwd
 * @create 2018-07-22 14:53
 **/
public interface UserDao {
    public User getById(@Param("id") Integer id);

    public Map<String, Object> getByIdReturnMap(Integer id);

    @Select("select * from db_user where id = #{param[0]} and user_name=#{param1[1]}")
    public User getByList(List<Object> list);

    public List<User> getsByUserNameLike(String userName);

    @MapKey("id")
    public Map<Object, User> getsByAgeLtReturnMap(Integer age);

    public User getUserAndDept(Integer id);

    public User getStep(Integer id);

    public User getByIdAndUserName(@Param("id") Integer id, @Param("userName") String userName);

    public User selectByMap(@Param("map") Map<String, Object> map);

    public Integer insertUser(User user);

    public Integer insertorUpdateUser(User user);

    public Integer insertOrUpdateUserBatsh(List<User> list);

    public List<User> getUsersByDeptId(Integer deptId);

    public Integer addUser(User user);

    public Integer addUsers(List<User> users);

    public List<User> getUsers(Map<String, Object> mapParam);

    public void getPageByProcedure(OraclePage page);

    public int update(User user);
}
