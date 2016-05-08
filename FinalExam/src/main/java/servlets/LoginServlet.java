package servlets;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getRootLogger();
//            Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logging();

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject json = jsonReader.readObject();
//        System.out.println(json.getString("test"));

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonObject data = Json.createObjectBuilder()
                .add("success", true)
                .build();
        Json.createWriter(out).writeObject(data);
    }

    private void logging() throws IOException {
//        String pattern = "%m";
//        PatternLayout layout = new PatternLayout(pattern);
//
//        log.addAppender(appender);FileAppender appender = new FileAppender(layout);
        log.setLevel(Level.INFO);
        log.info("test from logger");
    }
}
