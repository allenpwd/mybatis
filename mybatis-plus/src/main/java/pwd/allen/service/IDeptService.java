package pwd.allen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pwd.allen.entity.Department;

/**
 * @author 门那粒沙
 * @create 2020-06-26 10:38
 **/
public interface IDeptService extends IService<Department>, IPagerService<Department> {
}
