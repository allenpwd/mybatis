package pwd.allen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pwd.allen.mapper.CommonMapper;
import pwd.allen.service.IPagerService;
import pwd.allen.util.Pager;

/**
 * @author lenovo
 * @create 2020-09-09 10:35
 **/
public class BaseService<M extends CommonMapper<T>, T> extends ServiceImpl<M, T> implements IPagerService<T> {

    public IPage<T> selectByPager(Pager<T> pager) {
        return baseMapper.selectByPager(pager);
    }
}
