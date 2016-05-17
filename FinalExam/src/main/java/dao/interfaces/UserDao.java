package dao.interfaces;


import model.User;

import java.util.Optional;

public interface UserDao extends Dao{
    void setNewUser(String login, String first_name, String last_name, String password, int role_id);
    boolean checkIsUserExists(String login);
    Optional<User> getUserByLogin(String login);
}
