package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String first_name;
    private String last_name;
    private String password;
    private Role role;

    public User(int id, String login, String first_name, String last_name, String password) {

    }
}
