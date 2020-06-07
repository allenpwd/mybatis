package pwd.allen.dao;

import org.apache.ibatis.annotations.Select;
import pwd.allen.entity.User;

/**
 * 使用注解来映射简单语句会使代码显得更加简洁，但对于稍微复杂一点的语句，Java 注解不仅力不从心，还会让你本就复杂的 SQL 语句更加混乱不堪
 *
 * @author pwd
 * @create 2018-07-22 14:53
 **/
public interface UserDaoAnnotation {
    @Select("select * from db_user where id=#{userId}")
    public User selectUser(Integer userId);
}
