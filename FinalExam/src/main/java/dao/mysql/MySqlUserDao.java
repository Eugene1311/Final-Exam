package dao.mysql;

import common.functions.EncryptPassword;
import dao.interfaces.UserDao;
import model.Role;
import model.User;

import java.sql.SQLException;
import java.util.Optional;

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
    default boolean checkIsUserExists(String login) {
        try {
            return executeQuery("SELECT COUNT(*) FROM users WHERE login = '" + login + "'",
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
        return executeQuery("SELECT u.id, u.first_name, u.last_name, r.role, r.id AS role_id " +
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

//    default Optional<User> getUserById(int id) {
//        return executeQuery(
//                "SELECT first_name, last_name, password, role_id" +
//                        " FROM Person WHERE id = " + id,
//    }
//
//    default Optional<String> getPasswordByEmail(String email) {
//        return executeQuery("SELECT password FROM Person WHERE email = '" + email + "'",
//                rs -> rs.next() ? rs.getString("password") : null
//        ).toOptional();
//    }
//
//    default boolean setPasswordByEmail(String email, String password) {
//        return withStatement(statement ->
//                1 <= statement.executeUpdate(
//                        "UPDATE Person SET password = '" + password + "' WHERE email = '" + email + "'")
//        ).toOptional().orElse(false);
//    }
}
