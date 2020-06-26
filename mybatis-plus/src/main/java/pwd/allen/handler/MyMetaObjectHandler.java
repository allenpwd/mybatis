package pwd.allen.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自定义公共字段填充处理器
 *
 * @author 门那粒沙
 * @create 2020-06-25 23:25
 **/
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("msg", metaObject);
        if (fieldValue == null) {
            System.out.println("******插入操作 满足填充条件*******");
            strictInsertFill(metaObject, "msg", byte[].class, "默认填充值".getBytes());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
