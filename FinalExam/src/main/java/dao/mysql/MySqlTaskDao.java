package dao.mysql;

import dao.interfaces.TaskDao;
import model.Account;
import model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

@FunctionalInterface
public interface MySqlTaskDao extends TaskDao {
    @Override
    default boolean setNewTask(String title, String description, String customer_login) {
        return withPreparedStatement(
                "INSERT INTO tasks (customer_id, title, description, checked)" +
                        " VALUES ((SELECT id FROM users WHERE login = ?), ?, ?, 0)",
                preparedStatement -> {
                    preparedStatement.setString(1, customer_login);
                    preparedStatement.setString(2, title);
                    preparedStatement.setString(3, description);
                    return preparedStatement.executeUpdate() == 1;
                }).getOrThrowUnchecked();
    }

    @Override
    default Collection<Task> getAllTasks() {
        return executeQuery(
                "SELECT id, customer_id, title, description, checked, created_at, edited_at, account_id " +
                        "FROM tasks",
                rs -> {
                    Collection<Task> tasks = new TreeSet<>();
                    boolean checked = false;
                    while (rs.next()) {
                        checked = rs.getInt("checked") != 0;
                        tasks.add(new Task(rs.getInt("id"),
                                rs.getString("customer_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                checked,
                                rs.getDate("created_at").toLocalDate(),
                                rs.getDate("edited_at"),
                                new Account(rs.getInt("account_id"))
                                ));
                    }
                    return tasks;
                }).toOptional().orElse(Collections.emptySet());
    }

    @Override
    default Collection<Task> getAllTasksByLogin(String customer_login) {
        return executeQuery(
                "SELECT id, customer_id, title, description, checked, created_at, edited_at, account_id " +
                        "FROM tasks WHERE customer_id = " +
                        "(SELECT id FROM users WHERE login = '" + customer_login + "')",
                rs -> {
                    Collection<Task> tasks = new TreeSet<>();
                    boolean checked = false;
                    while (rs.next()) {
                        checked = rs.getInt("checked") != 0;
                        tasks.add(new Task(rs.getInt("id"),
                                rs.getString("customer_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                checked,
                                rs.getDate("created_at").toLocalDate(),
                                rs.getDate("edited_at"),
                                new Account(rs.getInt("account_id"))));
                    }
                    return tasks;
                }).toOptional().orElse(Collections.emptySet());
    }
}
