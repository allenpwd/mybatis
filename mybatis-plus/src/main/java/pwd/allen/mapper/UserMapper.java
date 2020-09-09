package pwd.allen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import pwd.allen.entity.User;

/**
 * @author 门那粒沙
 * @create 2020-06-16 22:18
 **/
public interface UserMapper extends CommonMapper<User> {

    /**
     * xml配置方式
     * @param id
     * @return
     */
    public User getUserWithDept(Integer id);

    /**
     * 自定义分页，传递参数 Page 即自动分页,必须放在第一位
     * 分页返回的对象与传入的对象是同一个
     * @param page
     * @param user
     * @return
     */
    public IPage<User> selectByPage(IPage<User> page, @Param("user") User user);
}
