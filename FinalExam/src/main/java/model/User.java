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
}
