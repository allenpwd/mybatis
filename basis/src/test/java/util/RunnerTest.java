package util;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.jdbc.SqlRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 门那粒沙
 * @create 2020-06-08 22:05
 **/
public class RunnerTest {

    private Connection connection;

    @Before
    public void init() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties"));

        String sql = "select * from db_user where id=?";

        connection = DriverManager.getConnection(properties.getProperty("jdbc.url")
                , properties.getProperty("jdbc.username")
                , properties.getProperty("jdbc.password"));
    }

    /**
     * 使用ScriptRunner 执行 init.sql
     */
    @Test
    public void scriptRunner() {
        ScriptRunner scriptRunner = new ScriptRunner(connection);

        //设置 sql分隔符，默认分号
        scriptRunner.setDelimiter(";");
        scriptRunner.setStopOnError(false);

        scriptRunner.runScript(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("sql/init.sql")));

        scriptRunner.closeConnection();

    }

    /**
     * 使用 SqlRunner 执行sql
     * @throws SQLException
     */
    @Test
    public void sqlRunner() throws SQLException {
        SqlRunner sqlRunner = new SqlRunner(connection);

//        String insertSql = new SQL().INSERT_INTO("db_user")
//                .VALUES("id,user_name", "?,?")
//                .VALUES("age", "?")
//                .VALUES("status,create_at", "1,?")
//                .VALUES("dept_id", "?").toString();
//        System.out.println("执行insert：" + sqlRunner.insert(insertSql, 20, "菠萝蜜", 21, new Date(), 1));

        String querySql = new SQL().SELECT("u.*")
                .SELECT("d.dept_name")
                .FROM("db_user u", "db_dept d")
                .WHERE("u.dept_id = d.id")
                .WHERE("u.id=?").toString();

        System.out.println("执行查询：" + sqlRunner.selectOne(querySql, 1));

        sqlRunner.closeConnection();
    }
}
