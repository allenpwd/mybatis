package pwd.allen.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import pwd.allen.mybatis.entity.User;

/**
 * @author pwd
 * @create 2018-07-22 14:53
 **/
public interface UserDao {
    public User getById(@Param("id") Integer id);
}
