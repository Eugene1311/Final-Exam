package servlets;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie login = new Cookie("login", "");
        login.setMaxAge(0);
        resp.addCookie(login);

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonObject data = Json.createObjectBuilder()
                .add("success", true)
                .build();
        Json.createWriter(out).writeObject(data);
    }
}
