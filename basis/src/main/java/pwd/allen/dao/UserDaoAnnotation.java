package pwd.allen.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import pwd.allen.entity.User;

/**
 * @author pwd
 * @create 2018-07-22 14:53
 **/
public interface UserDaoAnnotation {
    @Select("select * from db_user where id=#{userId}")
    public User selectUser(Integer userId);
}
