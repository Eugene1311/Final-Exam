package filters;

import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@WebFilter("*.jsp")
public class MainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //TODO remove this method and destroy also
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            Cookie cookie = new Cookie("locale", "en");
            response.addCookie(cookie);
        }

        response.setContentType("text/html; charset=utf-8");
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }
}
