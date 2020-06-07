import org.apache.ibatis.type.BigIntegerTypeHandler;
import org.junit.Test;
import pwd.allen.typeHandler.MyTypeHandler;
import pwd.allen.util.StatusEnum;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author 门那粒沙
 * @create 2020-06-07 8:43
 **/
public class OtherTest {

    interface TypeA<T> {
    }

    abstract class TypeB<T> implements TypeA<T> {
        public abstract T get(T t);
    }

    class TypeC extends TypeB<Integer> {
        @Override
        public Integer get(Integer integer) {
            return integer;
        }
    }

    class TypeD extends TypeC {}

    @Test
    public void one() {
        MyTypeHandler typeHandler = new MyTypeHandler();
        System.out.println(typeHandler.getRawType());

        Type genericSuperclass = TypeC.class.getGenericSuperclass();

        Type genericSuperclass1 = TypeD.class.getGenericSuperclass();
    }


    @Test
    public void two() {
        System.out.println(Arrays.toString(StatusEnum.class.getEnumConstants()));

        System.out.println(Enum.valueOf(StatusEnum.class, "ENABLED"));
        System.out.println(StatusEnum.DISABLED.ordinal());
    }
}
