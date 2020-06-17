package pwd.allen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author lenovo
 * @create 2020-06-17 11:13
 **/
public enum StatusEnum {

    ENABLED(0, "启用"), DISABLED(1, "禁用");

    /**
     * 注解在枚举字段上
     */
    @EnumValue
    private final int code;
    private final String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
