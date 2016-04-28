package servlets;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@WebServlet("/test-json")
public class TestJsonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
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

        String title = myResources.getString("local.title");
        String signInButton = myResources.getString("local.signInButton");
        String signUpButton = myResources.getString("local.signUpButton");

        JsonObject data = Json.createObjectBuilder()
                .add("title", title)
                .add("signInButton", signInButton)
                .add("signUpButton", signUpButton)
                .build();

        Json.createWriter(out).writeObject(data);
    }
}
