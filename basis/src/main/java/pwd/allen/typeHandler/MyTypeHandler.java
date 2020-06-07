package pwd.allen.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义 string 类型处理器，只有在字段指明jdbcType=VARCHAR才会生效
 *
 *  java类型：MyBatis通过泛型能得知该类型处理器处理的 Java 类型，也可以直接指定，有以下两种方式
 *      方式一：在类型处理器的配置元素（typeHandler 元素）上增加一个 javaType 属性（比如：javaType="String"），优先于方式二
 *      方式二：在类型处理器的类上增加一个 @MappedTypes 注解指定与其关联的 Java 类型列表
 *  jdbc类型：有以下两种方式来指定
 *      方式一：在类型处理器的配置元素上增加一个 jdbcType 属性（比如：jdbcType="VARCHAR"）；优先于方式二
 *      方式二：在类型处理器的类上增加一个 @MappedJdbcTypes 注解指定与其关联的 JDBC 类型列表
 *  还可以在配置resultMap的字段时用typeHandler属性指定类型处理器：<result column="user_name" property="userName" typeHandler="pwd.allen.typeHandler.MyTypeHandler"/>
 *
 * 类型处理器的作用：在设置预处理语句（PreparedStatement）中的参数或从结果集中取出一个值时， 会用类型处理器将获取到的值以合适的方式转换成 Java 类型
 *
 * includeNullJdbcType 属性：MyBatis 不会通过检测数据库元信息来决定使用哪种类型，如果字段没有指明jdbcType，则jdbcType=null；所以如果includeNullJdbcType=false，则该类型处理器不对jdbcType=null的字段生效
 *
 * @author 门那粒沙
 * @create 2020-06-07 10:14
 **/
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = false)   //指定关联的 JDBC 类型
//@MappedTypes({String.class})
public class MyTypeHandler extends BaseTypeHandler<String> {

    private static final String PREFIX = "[pwd]";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return PREFIX + rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return PREFIX + rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return PREFIX + cs.getString(columnIndex);
    }
}
