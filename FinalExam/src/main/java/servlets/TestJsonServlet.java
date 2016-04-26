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
        PrintWriter out = response.getWriter();
        if(Objects.equals(request.getParameter("language"), "english")) {
            Locale locale = Locale.ENGLISH;
            ResourceBundle myResources = ResourceBundle.getBundle("locale",
                    locale);

            String title = myResources.getString("local.title");
            String signInButton = myResources.getString("local.signInButton");
            String signUpButton = myResources.getString("local.signUpButton");

//            JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
//            JsonGenerator gen = factory.createGenerator(System.out);
//            gen.writeStartObject()
//                    .write("title", title)
//                    .write("signInButton", signInButton)
//                    .write("signUpButton", signUpButton)
//                .writeEnd();
//            out.println(gen);
//            out.println("signInButton :" + signInButton);
//            out.println("signUpButton :" + signUpButton);

            JsonObject data = Json.createObjectBuilder()
                    .add("title", title)
                    .add("signInButton", signInButton)
                    .add("signUpButton", signUpButton)
                    .build();

            Json.createWriter(out).writeObject(data);
        }

//        out.println(request.getParameter("language"));
    }
}
