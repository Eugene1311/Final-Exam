package rest;

import dao.interfaces.TaskDao;
import listeners.DBInitializer;
import lombok.extern.log4j.Log4j;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("tasks")
@Produces({"application/json"})
@Log4j
public class TasksResourse {
    private TaskDao taskDao;

    @Context
    public void setContext(ServletContext context) {
        taskDao = (TaskDao) context.getAttribute(DBInitializer.TASK_DAO);
    }

    @GET
    @Produces({"application/json"})
    public Response getAllTasks() {
        return Response.ok().build();
    }

    @POST
    @Path("new")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createNewTask(@Context HttpServletRequest req, @CookieParam("login") String login) {
        JsonReader jsonReader;
        if (login == null) {
            return Response.ok().entity("fail").build();
        }
        // TODO: 19.05.2016 разобраться с Respons'om
        try {
            jsonReader = Json.createReader(req.getInputStream());
            JsonObject json = jsonReader.readObject();
            String title = json.getString("taskTitle");
            String description = json.getString("taskDescription");

            boolean result = taskDao.setNewTask(title, description, login);
            if (result) {
                return Response.ok("success").build();
            } else {
                return Response.ok("error").build();
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
            return Response.ok().entity("fail").build();
        }
    }

//    @POST
//    @Path("new")
//    @Consumes({"application/json"})
//    @Produces({"application/json"})
//    public Response createNewTask(InputStream data) {
//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(data));
//        String line;
//        try {
//            while((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        log.info(sb);
//        return Response.ok().build();
//    }

//        JsonParser parser = Json.createParser(data);
//        while (parser.hasNext()) {
//            switch (parser.next()) {
//                case KEY_NAME:
//                    log.info("key :" + parser.getString());
//                    parser.next();
//                    break;
//                case VALUE_STRING:
//                    log.info("value :" + parser.getString());
//                    parser.next();
//                    break;
//            }
//        }
//        log.info(data.getString("taskTitle"));
//        log.info(data.getString("taskDescription"));
}
