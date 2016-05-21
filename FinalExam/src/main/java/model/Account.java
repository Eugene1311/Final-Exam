package model;

import lombok.Data;

@Data
public class Account {
    private final int id;
    private int value;
    private boolean accepted;
}
