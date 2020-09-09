package pwd.allen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenovo
 * @create 2020-09-09 10:43
 **/
public interface CommonMapper<T> extends BaseMapper<T> {

    /**
     * 自定义分页，传递参数 Page 即自动分页,必须放在第一位
     * 分页返回的对象与传入的对象是同一个
     * @param page
     * @return
     */
    public IPage<T> selectByPager(IPage<T> page);
}
