package dao.interfaces;

public interface TaskDao extends Dao{
    boolean setNewTask(String title, String description, String customer_login);
}
