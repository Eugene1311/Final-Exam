package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.UserDao;
import listeners.DBInitializer;
import lombok.extern.log4j.Log4j;
import model.User;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.ok;

@Path("users")
@Produces({"application/json"})
@Log4j
public class UsersResource {
    private UserDao userDao;

    @Context
    public void setContext(ServletContext context) {
        userDao = (UserDao) context.getAttribute(DBInitializer.USER_DAO);
    }

    @GET
    @Path("{role}")
    @Produces({"application/json"})
    public Response getAllUsersByRole(@PathParam("role")String role) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> response = new HashMap<>();
        String jsonResponse = null;
        int roleId = 0;

        switch (role) {
            case "customers":
                roleId = 3;
                break;
            case "developers":
                roleId = 1;
        }
        Collection<User> users = userDao.getAllUsersByRole(roleId);

        response.put("success", Boolean.TRUE);
        response.put("result", users);

        try {
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error(e);
            e.printStackTrace();
        }

        return ok().entity(jsonResponse).build();
    }
}
