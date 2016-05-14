package model;

import lombok.Data;

@Data
public class User {
    private final int id;
    private String first_name;
    private String last_name;
    private String password;
    private Role role;
}
