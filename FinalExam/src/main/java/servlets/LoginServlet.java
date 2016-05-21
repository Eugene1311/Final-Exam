package servlets;

import dao.interfaces.RoleDao;
import dao.interfaces.UserDao;
import listeners.DBInitializer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        RoleDao roleDao = (RoleDao) servletContext.getAttribute(DBInitializer.ROLE_DAO);
        UserDao userDao = (UserDao) servletContext.getAttribute(DBInitializer.USER_DAO);

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject json = jsonReader.readObject();

        int role_id = Integer.parseInt(json.getString("role"));
        String login = json.getString("login");

        boolean isUserExists = userDao.checkIsUserExists(login);

        if (!isUserExists) {
            JsonObject data = Json.createObjectBuilder()
                    .add("loginReserved", true)
                    .build();
            Json.createWriter(out).writeObject(data);
        } else {
            userDao.setNewUser(login, json.getString("firstName"), json.getString("lastName"), json.getString("password"), role_id);

            Optional<String> role = roleDao.getRoleNameById(role_id);

            String roleName;
            if (role.isPresent()) {
                roleName = role.get();
            } else {
                roleName = "no such role";
            }

            JsonObject data = Json.createObjectBuilder()
                    .add("role", roleName)
                    .add("login", login)
                    .build();
            Json.createWriter(out).writeObject(data);

            Cookie cookie = new Cookie("login", login);
            resp.addCookie(cookie);
        }
    }
}
