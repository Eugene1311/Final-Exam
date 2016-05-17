package common.functions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ResourceBundle;
import java.util.Set;


public class JsonHelper {
    public static JsonObject getLocalData(ResourceBundle myResources) {
        Set<String> keys = myResources.keySet();
        JsonObjectBuilder object = Json.createObjectBuilder();

        for (String key : keys)
            object.add(key.replace("local.", ""), myResources.getString(key));

        return object.build();
    }
}
