package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Comparable {
    private int id;
    private String login;
    private String first_name;
    private String last_name;
    private String password;
    private Role role;

    public User(int id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        if (id < user.id) {
            return -1;
        } else if(id > user.id) {
            return 1;
        }

        return 0;
    }
}
