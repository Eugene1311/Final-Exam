package dao.interfaces;

import model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskDao extends Dao{
    boolean setNewTask(String title, String description, String customer_login);
    Collection<Task> getAllTasksByLogin(String customer_login);
    Collection<Task> getAllTasks();
    Optional<Task> getTaskById(int id);
}
