import model.Task;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TestDB {
    private String url = "jdbc:mysql://localhost:3306/projects_manager?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&" +
            "zeroDateTimeBehavior=convertToNull&" +
            "useSSL=true&user=root&password=root";
    private Connection connection;

    @Before
    public void getConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

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
    public void testGetUserByLogin() throws SQLException {
        String login = "eug";
        String query = "SELECT u.id, u.first_name, u.last_name, u.password, r.role, r.id AS role_id"
                + " FROM Users u, Roles r WHERE login = '" + login +"' AND r.id = u.role_id";
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {
            while(rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("first_name") + " " +
                        rs.getString("last_name") + " " +
                        rs.getString("password") + " " +
                        rs.getString("role_id") + " " +
                        rs.getString("role")
                );
            }
        }
    }

    @Test
    public void testSetNewTask() throws SQLException {
        String query = "INSERT INTO tasks (customer_id, title, description, checked)" +
                " VALUES ((SELECT id FROM users WHERE login = 'customer'), 'test', 'test', 0)";
        try(Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(query);
            System.out.println(result);
        }
    }

    @Test
    public void testGetAllTasks() throws SQLException {
        String query = "SELECT id, customer_id, title, description, checked, created_at, edited_at " +
                "FROM tasks";
        execute(query);
    }

    @Test
    public void testGetAllTasksByLogin() throws SQLException {
        String query = "SELECT id, customer_id, title, description, checked, created_at, edited_at " +
                "FROM tasks WHERE customer_id = " +
                "(SELECT id FROM users WHERE login = 'customer')";
        execute(query);
    }

    @Test
    public void testGetAllUsersByRole() throws SQLException {
        String query = "SELECT id, first_name, last_name FROM users WHERE role_id = 1";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            Collection<User> users = new TreeSet<>();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"))
                );
                System.out.println(rs.getString("first_name") + " " +
                        rs.getString("last_name"));
            }
            users.forEach(System.out::println);
        }
    }

    private void execute(String query) throws SQLException {
        Collection<Task> tasks = new TreeSet<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                boolean checked = rs.getInt("checked") != 0;
                Task task = new Task(rs.getInt("id"),
                        rs.getString("customer_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        checked,
                        rs.getDate("created_at").toLocalDate(),
                        rs.getDate("edited_at"));
                tasks.add(task);
            }
        }
        tasks.forEach(System.out::println);
        System.out.println(tasks.size());
    }
}