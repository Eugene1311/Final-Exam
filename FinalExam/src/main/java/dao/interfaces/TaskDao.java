package dao.interfaces;

import model.Task;

import java.util.Collection;

public interface TaskDao extends Dao{
    boolean setNewTask(String title, String description, String customer_login);
    Collection<Task> getAllTasksByLogin(String customer_login);
    Collection<Task> getAllTasks();
}
