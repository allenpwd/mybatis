package pwd.allen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pwd.allen.entity.User;

/**
 * @author 门那粒沙
 * @create 2020-06-20 21:57
 **/
public interface IUserService extends IService<User>, IPagerService<User> {

    User getUserWithDept(Integer id);
}
