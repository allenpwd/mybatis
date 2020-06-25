package pwd.allen.util;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;

/**
 * 有两个方法
 * nextId：支持主键类型是long,Integer,String，支持的策略是ASSIGN_ID
 * nextUUID：支持主键类型是String，支持的策略是ASSIGN_UUID
 *
 * @author 门那粒沙
 * @create 2020-06-25 15:32
 **/
public class MyIdGenerator implements IdentifierGenerator {

    private final Sequence sequence = new Sequence();;

    @Override
    public Number nextId(Object entity) {
        return (int)sequence.nextId();
    }
}
