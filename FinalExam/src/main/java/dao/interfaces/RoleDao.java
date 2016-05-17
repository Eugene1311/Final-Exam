package dao.interfaces;


import java.util.Optional;

public interface RoleDao extends Dao{
    Optional<String> getRoleNameById(int id);
}
