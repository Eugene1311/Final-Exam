package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.TaskDao;
import listeners.DBInitializer;
import lombok.extern.log4j.Log4j;
import model.Task;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static javax.ws.rs.core.Response.ok;

@Path("tasks")
@Produces({"application/json"})
@Log4j
public class TasksResource {
    private TaskDao taskDao;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String,Object> response = new HashMap<>();
    private String jsonResponse;

    @Context
    public void setContext(ServletContext context) {
        taskDao = (TaskDao) context.getAttribute(DBInitializer.TASK_DAO);
    }

    @GET
    @Produces({"application/json"})
    public Response getAllTasks() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> response = new HashMap<>();
        String jsonResponse = null;

        final Collection<Task> tasks = taskDao.getAllTasks();

        try {
            response.put("success", Boolean.TRUE);
            response.put("result", tasks);
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error(e);
            e.printStackTrace();
        }
        return ok().entity(jsonResponse).build();
    }

    @GET
    @Path("customer")
    @Produces({"application/json"})
    public Response getAllTasksByLogin(@CookieParam("login") String login) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> response = new HashMap<>();
        String jsonResponse = null;
        try {
            if (login == null) {
                response.put("success", Boolean.FALSE);
                jsonResponse = mapper.writeValueAsString(response);
                return ok().entity(jsonResponse).build();
            }

            final Collection<Task> tasks = taskDao.getAllTasksByLogin(login);
            response.put("success", Boolean.TRUE);
            response.put("result", tasks);
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error(e);
            e.printStackTrace();
        }
        return ok().entity(jsonResponse).build();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Response getTaskById(@PathParam("id")int id) {
        Optional<Task> task = taskDao.getTaskById(id);

        if (task.isPresent()) {
            response.put("success", Boolean.TRUE);
            response.put("result", task.get());
        } else {
            response.put("success", Boolean.FALSE);
        }
        try {
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error(e);
            e.printStackTrace();
        }
        return ok().entity(jsonResponse).build();
    }

    @POST
    @Path("new")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createNewTask(@Context HttpServletRequest req, @CookieParam("login") String login) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> response = new HashMap<>();
        String jsonResponse;
        if (login == null) {
            response.put("success", Boolean.FALSE);
            return ok().entity(response).build();
        }

        try {
            JsonReader jsonReader = Json.createReader(req.getInputStream());
            JsonObject json = jsonReader.readObject();
            String title = json.getString("taskTitle");
            String description = json.getString("taskDescription");

            boolean result = taskDao.setNewTask(title, description, login);
            if (result) {
                response.put("success", Boolean.TRUE);
            } else {
                response.put("success", Boolean.FALSE);
            }
            jsonResponse = mapper.writeValueAsString(response);
            return ok(jsonResponse).build();
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
