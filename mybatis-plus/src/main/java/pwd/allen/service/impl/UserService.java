package pwd.allen.service.impl;

import org.springframework.stereotype.Service;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;
import pwd.allen.service.IUserService;

/**
 * @author 门那粒沙
 * @create 2020-06-20 22:01
 **/
@Service
public class UserService extends BaseService<UserMapper, User> implements IUserService {

    @Override
    public User getUserWithDept(Integer id) {
        return baseMapper.getUserWithDept(id);
    }
}
