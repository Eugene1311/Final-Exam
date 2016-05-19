package dao.mysql;

import dao.interfaces.TaskDao;

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
}
