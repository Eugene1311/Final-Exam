package servlets;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
//@WebServlet ("*.jsp")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MainServlet");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            log.info(cookie.getName() + " : " + cookie.getValue());
        }
        response.setContentType("text/html");
        request.getRequestDispatcher("/index.jsp").include(request, response);
    }
}