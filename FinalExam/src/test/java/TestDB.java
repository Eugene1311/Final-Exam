import org.junit.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestDB {
    private String url = "jdbc:mysql://localhost:3306/projects_manager?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&" +
            "useSSL=true&user=root&password=root";
    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    @Test
    public void testDriver() throws SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select now()")) {
            System.out.println(connection.getClass().getCanonicalName());
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
        }
    }

    @Test
    public void testQuery() throws IOException, SQLException {
        final Path path = Paths.get("src/test/resources/init.sql");
        final String[] sqls = Files.lines(path)
                .collect(Collectors.joining()).split(";");
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            Arrays.stream(sqls).forEach((ExceptionalConsumer<String, ?>) statement::addBatch);
            statement.executeBatch();
        }
    }

    @Test
    public void testDataSource () {
        System.out.println(ds);
//        try(Connection connection = ds.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("select now()")) {
//            System.out.println(connection.getClass().getCanonicalName());
//            while(rs.next()) {
//                System.out.println(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
