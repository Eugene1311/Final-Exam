package dao.mysql;

import common.functions.EncryptPassword;
import common.functions.Exceptional;
import dao.interfaces.UserDao;
import model.Role;
import model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.TreeSet;

@FunctionalInterface
public interface MySqlUserDao extends UserDao {

    @Override
    default void setNewUser(String login, String first_name, String last_name, String password, int role_id) {
        executeUpdate("INSERT INTO users (login, first_name, last_name, password, role_id) VALUES ('" +
                login + "','" +
                first_name + "','" +
                last_name + "','" +
                EncryptPassword.encryptPassword(password) + "'," +
                role_id +
                ")"
        );
    }

    @Override
    default Optional<String> checkIsUserExists(String login, String first_name, String last_name, String password) {
        String psw = EncryptPassword.encryptPassword(password);
        return executeQuery("SELECT role FROM roles r, users u " +
                            "WHERE (r.id = u.role_id) AND (u.login = '" + login + "') " +
                            "AND (u.first_name = '" + first_name + "') AND (u.last_name = '" + last_name + "') " +
                            "AND (u.password = '" + psw + "')",
                    rs -> rs.next()?
                            rs.getString("role")
                            : null
                    ).toOptional();
    }

    @Override
    default boolean checkIsLoginExists(String login) {
        try {
            return executeQuery("SELECT COUNT(id) FROM users WHERE login = '" + login + "'",
                    rs -> {
                        rs.next();
                        return rs.getInt(1) == 0;
                    }).getOrThrow();
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    default Optional<User> getUserByLogin(String login) {
        return executeQuery("SELECT u.id, u.first_name, u.last_name, u.password, r.role, r.id AS role_id " +
                        "FROM users u, roles r WHERE login = '" + login + "' AND r.id = u.role_id",
                rs -> rs.next() ?
                        new User(rs.getInt("id"),
                            login,
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("password"),
                                new Role(
                                    rs.getInt("role_id"),
                                    rs.getString("role")
                                )
                ) : null).toOptional();
    }

    @Override
    default Collection<User> getAllUsersByRole(int roleId) {
        return executeQuery("SELECT id, first_name, last_name FROM users WHERE role_id = '" + roleId + "'",
                rs -> {
                    Collection<User> users = new TreeSet<>();
                    while (rs.next()) {
                        users.add(new User(rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"))
                        );
                    }
                    return users;
                }).toOptional().orElse(Collections.emptySet());
    }

    @Override
    default boolean changeUserData(String current_login, String new_login, String new_first_name, String new_last_name) {
        Exceptional<Integer, SQLException> count = executeUpdate("UPDATE users SET login = '" + new_login + "', first_name = '" + new_first_name +
                "', last_name = '" + new_last_name + "' " +
                "WHERE login = '" + current_login + "'");

        return count.toOptional().isPresent();
    }
}
