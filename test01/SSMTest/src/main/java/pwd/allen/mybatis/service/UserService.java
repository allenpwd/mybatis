package pwd.allen.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwd.allen.mybatis.dao.UserDao;
import pwd.allen.mybatis.entity.User;

/**
 * @author pwd
 * @create 2018-10-07 16:29
 **/
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUser(Integer id) {
        return userDao.getById(id);
    }

}
