package dao.mysql;

import dao.interfaces.RoleDao;

import java.util.Optional;

@FunctionalInterface
public interface MySqlRoleDao extends RoleDao {

    @Override
    default Optional<String> getRoleNameById(int id) {
        return executeQuery(
                "SELECT role FROM Roles WHERE id = " + id,
                rs -> rs.next()
                        ? rs.getString("role")
                        : null
        ).toOptional();
    }
}
