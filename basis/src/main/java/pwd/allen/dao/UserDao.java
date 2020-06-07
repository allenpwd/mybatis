package pwd.allen.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pwd.allen.entity.User;
import pwd.allen.util.MyPage;
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

    public Integer insertOrUpdateUser(User user);

    public Integer insertOrUpdateUserBatsh(List<User> list);

    public List<User> getUsersByDeptId(Integer deptId);

    public Integer addUser(User user);

    public List<User> getUsers(Map<String, Object> mapParam);

    public void getPageByProcedure4Oracle(MyPage page);

    /**
     * 很鸡肋，总记录数回写到MyPage，但是查询列表不知道怎么回写，只能单独作为返回值
     * @param page
     * @return
     */
    public List<User> getPageByProcedure4Mysql(MyPage page);

    public int update(User user);
}
