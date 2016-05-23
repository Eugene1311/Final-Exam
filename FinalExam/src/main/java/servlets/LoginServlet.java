package servlets;

import dao.interfaces.RoleDao;
import dao.interfaces.UserDao;
import listeners.DBInitializer;
import lombok.extern.log4j.Log4j;

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
@Log4j
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

        boolean isLoginExists = userDao.checkIsLoginExists(login);

        if (!isLoginExists) {
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

            setCookie(resp, login);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        UserDao userDao = (UserDao) servletContext.getAttribute(DBInitializer.USER_DAO);

        String login = req.getParameter("login");
        String first_name = req.getParameter("firstName");
        String last_name = req.getParameter("lastName");
        String password = req.getParameter("password");

        Optional<String> role = userDao.checkIsUserExists(login, first_name, last_name, password);

        JsonObject data;

        if(role.isPresent()) {
            data = Json.createObjectBuilder()
                    .add("success", true)
                    .add("role", role.get())
                    .build();
        } else {
            data = Json.createObjectBuilder()
                    .add("success", false)
                    .build();
        }

        Json.createWriter(out).writeObject(data);
        setCookie(resp, login);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        UserDao userDao = (UserDao) servletContext.getAttribute(DBInitializer.USER_DAO);

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject json = jsonReader.readObject();

        String current_login = json.getString("current_login");
        String new_login = json.getString("new_login");
        String new_first_name = json.getString("new_first_name");
        String new_last_name = json.getString("new_last_name");

        boolean isUpdated = userDao.changeUserData(current_login, new_login, new_first_name, new_last_name);

        JsonObject data = Json.createObjectBuilder()
                .add("success", isUpdated)
                .build();
        Json.createWriter(out).writeObject(data);

        if(isUpdated) {
            setCookie(resp, new_login);
        }
    }

    private void setCookie(HttpServletResponse resp, String login) {
        Cookie cookie = new Cookie("login", login);
        resp.addCookie(cookie);
    }
}
