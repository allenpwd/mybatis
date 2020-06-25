package pwd.allen.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 通过继承IEnum声明通用枚举
 *
 * @author lenovo
 * @create 2020-06-17 11:13
 **/
public enum StatusEnum2 implements IEnum<Integer> {

    ENABLED(0, "启用"), DISABLED(1, "禁用");

    private final int code;
    private final String msg;

    StatusEnum2(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
