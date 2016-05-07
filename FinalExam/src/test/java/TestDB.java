import org.junit.Test;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Евгений on 06.05.2016.
 */
public class TestDB {
    private String url = "jdbc:mysql://localhost:3306/projects_manager?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&" +
            "useSSL=true&user=root&password=root";

    @Test
    public void testDriver() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url);
        System.out.println(connection.getClass().getCanonicalName());

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select now()");

        while(resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        connection.close();
    }

    @Test
    public void testQuery() throws IOException, SQLException {
//        final Path path = Paths.get("src/test/resources/init.sql");
//        final String[] sqls = Files.lines(path)
//                .collect(Collectors.joining()).split(";");
//        try (Connection connection = DriverManager.getConnection(url);
//             Statement statement = connection.createStatement()) {
//            Arrays.stream(sqls).forEach((ExceptionalConsumer<String, ?>) statement::addBatch);
//            statement.executeBatch();
//        }
    }
}
