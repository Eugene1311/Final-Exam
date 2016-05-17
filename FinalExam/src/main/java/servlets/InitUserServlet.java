package servlets;

import dao.interfaces.UserDao;
import listeners.DBInitializer;
import model.User;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/user-data")
public class InitUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        UserDao userDao = (UserDao) servletContext.getAttribute(DBInitializer.USER_DAO);
        JsonObject data;

//        JsonReader jsonReader = Json.createReader(req.getReader());
//        JsonObject json = jsonReader.readObject();
//        String login = json.getString("login");
        Cookie[] cookies = req.getCookies();
        String login = "";
        for (Cookie cookie: cookies) {
            if ("login".equals(cookie.getName())) {
                login = cookie.getValue();
            }
        }

        if (!Objects.equals(login, "")) {
            Optional<User> userOptional = userDao.getUserByLogin(login);

            if (!userOptional.isPresent()) {
                data = Json.createObjectBuilder()
                        .add("success", false)
                        .build();
            } else {
                User user = userOptional.get();
                data = Json.createObjectBuilder()
                        .add("login", user.getLogin())
                        .add("first_name", user.getFirst_name())
                        .add("last_name", user.getLast_name())
                        .add("role", user.getRole().getRole())
                        .build();
            }
        } else {
            data = Json.createObjectBuilder()
                    .add("message", "no such user")
                    .build();
        }
        Json.createWriter(out).writeObject(data);
    }
}
