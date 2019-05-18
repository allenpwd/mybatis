package pwd.allen.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pwd.allen.mybatis.entity.User;
import pwd.allen.mybatis.service.UserService;

import java.util.Map;

/**
 * @author pwd
 * @create 2018-10-07 16:29
 **/
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getAll")
    public String getAll(Map<String, Object> map) {
        User user = userService.getUser(1);
        System.out.println("user---" + user);
        map.put("user", user);
        return "list";
    }

}
