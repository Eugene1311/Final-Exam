package listeners;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class DBInitializer implements ServletContextListener {
    @Resource(name="jdbc/ProdDB")
    private static DataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        final ServletContext servletContext = sce.getServletContext();
        try {
            Connection connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
