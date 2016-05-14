package servlets;

import functions.JsonHelper;
import lombok.extern.log4j.Log4j;

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
import java.util.ResourceBundle;

@WebServlet("/init")
@Log4j
public class InitialServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Cookie cooki = new Cookie("locale", "");
//        cooki.setMaxAge(0);
//        response.addCookie(cooki);

        Cookie[] cookies = request.getCookies();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        ResourceBundle myResources = null;

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    log.info(cookie.getName() + " : " + cookie.getValue());
                    switch (cookie.getValue()) {
                        case "en":
                            myResources = ResourceBundle.getBundle("locale", new Locale("en"));
                            break;
                        case "ru":
                            myResources = ResourceBundle.getBundle("locale", new Locale("ru"));
                    }
                }
            }
        } else {
            myResources = ResourceBundle.getBundle("locale", new Locale("en"));
        }
        JsonObject data = JsonHelper.getLocalData(myResources);
        Json.createWriter(out).writeObject(data);
    }

}
