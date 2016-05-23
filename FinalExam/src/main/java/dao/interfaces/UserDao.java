package dao.interfaces;


import model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao extends Dao{
    void setNewUser(String login, String first_name, String last_name, String password, int role_id);
    boolean checkIsLoginExists(String login);
    Optional<User> getUserByLogin(String login);
    Collection<User> getAllUsersByRole(int roleId);
    Optional<String> checkIsUserExists(String login, String first_name, String last_name, String password);
    boolean changeUserData(String current_login, String new_login, String new_first_name, String new_last_name);
}
