package servlets;

import lombok.extern.log4j.Log4j;

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
import java.util.Map;

@WebServlet("/login")
@Log4j
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        logging();

        JsonReader jsonReader = Json.createReader(req.getReader());
        JsonObject json = jsonReader.readObject();
        for (Map.Entry entry: json.entrySet()) {
            log.info(entry.getKey() + " : " + entry.getValue());
        }

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JsonObject data = Json.createObjectBuilder()
                .add("role", json.getString("role"))
                .build();
        Json.createWriter(out).writeObject(data);
    }

//    private void logging() throws IOException {
////        String prefix =  getServletContext().getRealPath("/");
////        System.out.println("prefix: " + prefix);
//        log.info("test from LoginServlet");
//    }
}
