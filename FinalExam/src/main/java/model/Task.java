package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {
    private final int id;
    private final String customer_id;
    private String title;
    private String description;
    private boolean checked;
    private final LocalDate created_at;
    private LocalDate edited_at;
}
