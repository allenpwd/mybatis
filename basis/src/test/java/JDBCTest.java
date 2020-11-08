import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author 门那粒沙
 * @create 2020-06-06 19:20
 **/
public class JDBCTest {

    private static final Logger logger = LoggerFactory.getLogger(JDBCTest.class);

    private Connection connection;

    @Before
    public void init() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));

        // mysql
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        // oracle
//        url = properties.getProperty("oracle.url");
//        username = properties.getProperty("oracle.username");
//        password = properties.getProperty("oracle.password");

        //可以省略，JDBC 4.0以后 DriverManager会加载并初始化jar里的Driver（SPI机制）
//        Class.forName(properties.getProperty("jdbc.driver"));

        // 获取连接
        connection = DriverManager.getConnection(url, username, password);
        logger.info("数据库名：{}", connection.getMetaData().getDatabaseProductName());
        logger.info("数据库版本：{}   【{} {}】", connection.getMetaData().getDatabaseProductVersion()
                , connection.getMetaData().getDatabaseMinorVersion()
                , connection.getMetaData().getDatabaseMajorVersion());
        logger.info("Driver：{}", connection.getMetaData().getDriverName());
        logger.info("supportsTransactions：{}", connection.getMetaData().supportsTransactions());
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * 测试查询
     *
     * @throws IOException
     * @throws SQLException
     */
    @Test
    public void one() throws IOException, SQLException {
        String sql = "select * from db_user where id=?";

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
    }

    @Test
    public void transcation() throws SQLException {
        // 设置不自动提交，即开启事务
        connection.setAutoCommit(false);

        String sql = "UPDATE db_user set user_name=? where id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "测试回滚咯咯");
        pstmt.setInt(2, 1);
        logger.info("更新：{}", pstmt.executeUpdate());

        try {
            System.out.println(1 / 0);

            // 提交
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚
            connection.rollback();
        }
    }
}
