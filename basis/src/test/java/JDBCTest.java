import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author 门那粒沙
 * @create 2020-06-06 19:20
 **/
public class JDBCTest {

    /**
     * 测试jdbc
     *
     * @throws IOException
     * @throws SQLException
     */
    @Test
    public void one() throws IOException, SQLException {

        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));

        //可以省略，DriverManager会加载并初始化jar里的Driver（SPI机制）
//        Class.forName(properties.getProperty("jdbc.driver"));

        String sql = "select * from db_user where id=?";

        //<editor-fold desc="mysql">
        //获取 mysql 连接
        Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.url")
                , properties.getProperty("jdbc.username")
                , properties.getProperty("jdbc.password"));
        System.out.println("数据库名：" + connection.getMetaData().getDatabaseProductName());
        System.out.println("数据库版本：" + connection.getMetaData().getDatabaseProductVersion()
                + "   【" + connection.getMetaData().getDatabaseMinorVersion() + "  " + connection.getMetaData().getDatabaseMajorVersion() + "】");
        System.out.println("Driver：" + connection.getMetaData().getDriverName());
        System.out.println("supportsTransactions：" + connection.getMetaData().supportsTransactions());

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, 1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            //大小写都可以
            System.out.println(rs.getString("USER_NAME"));
            System.out.println(rs.getString("create_at"));
        }
        rs.close();
        ps.close();
        connection.close();
        //</editor-fold>

        System.out.println("----------------------------------------------------------");

        //<editor-fold desc="oracle">
        //获取 oracle 连接
        connection = DriverManager.getConnection(properties.getProperty("oracle.url")
                , properties.getProperty("oracle.username")
                , properties.getProperty("oracle.password"));
        System.out.println("数据库名：" + connection.getMetaData().getDatabaseProductName());
        System.out.println("数据库版本：" + connection.getMetaData().getDatabaseProductVersion()
                + "   【" + connection.getMetaData().getDatabaseMinorVersion() + "  " + connection.getMetaData().getDatabaseMajorVersion() + "】");
        System.out.println("Driver：" + connection.getMetaData().getDriverName());
        System.out.println("supportsTransactions：" + connection.getMetaData().supportsTransactions());

        ps = connection.prepareStatement(sql);
        ps.setInt(1, 1);
        rs = ps.executeQuery();
        if (rs.next()) {
            //大小写都可以
            System.out.println(rs.getString("USER_NAME"));
            System.out.println(rs.getString("create_at"));
        }
        rs.close();
        ps.close();
        connection.close();
        //</editor-fold>
    }
}
