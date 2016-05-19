package listeners;

import dao.mysql.MySqlRoleDao;
import dao.mysql.MySqlTaskDao;
import dao.mysql.MySqlUserDao;
import lombok.extern.log4j.Log4j;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
@Log4j
public class DBInitializer implements ServletContextListener {
    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    public static final String USER_DAO = "userDao";
    public static final String ROLE_DAO = "roleDao";
    public static final String TASK_DAO = "taskDao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute(USER_DAO, (MySqlUserDao) ds::getConnection);
        servletContext.setAttribute(ROLE_DAO, (MySqlRoleDao) ds::getConnection);
        servletContext.setAttribute(TASK_DAO, (MySqlTaskDao) ds::getConnection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
