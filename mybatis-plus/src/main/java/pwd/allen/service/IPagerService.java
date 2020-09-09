package pwd.allen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import pwd.allen.util.Pager;

/**
 * @author lenovo
 * @create 2020-09-09 11:03
 **/
public interface IPagerService<T> {

    public IPage<T> selectByPager(Pager<T> pager);
}
