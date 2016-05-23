package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class Task implements Comparable{
    private final int id;
    private final int customer_id;
    private String title;
    private String description;
    private boolean checked;
    private final LocalDate created_at;
    private Date edited_at;
    private Account account;

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        if (id < task.id) {
            return -1;
        } else if(id > task.id) {
            return 1;
        }

        return 0;
    }
}
