package servlets;

import functions.JsonHelper;

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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@WebServlet("/localize")
public class LocalizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addCookie(new Cookie("locale", request.getParameter("language")));

        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        ResourceBundle myResources = null;
        
        if(Objects.equals(request.getParameter("language"), "en")) {
            myResources = ResourceBundle.getBundle("locale",
                    new Locale("en"));
        } else if(Objects.equals(request.getParameter("language"), "ru")) {
            myResources = ResourceBundle.getBundle("locale",
                    new Locale("ru"));
        }

        JsonObject data = JsonHelper.getLocalData(myResources);
        Json.createWriter(out).writeObject(data);
    }
}
