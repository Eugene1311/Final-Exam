import common.functions.EncryptPassword;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class Tests {
    private MyResources myResources;

    @Test
    public void testResourseBundle() throws UnsupportedEncodingException {
        ResourceBundle myResources = ResourceBundle.getBundle("locale", new Locale("en"));
//        System.out.println("local.title: " + myResources.getValue("local.title"));
//
//        myResources = new MyResources(new Locale("ru"));
//        System.out.println(new String(title.getBytes(), "UTF-8"));
//        System.out.println("local.title: " + myResources.getValue("local.title"));
        System.out.println(myResources.keySet());
    }

    @Test
    public void testJson() throws UnsupportedEncodingException {
        myResources = new MyResources(new Locale("ru"));
        String title = myResources.getValue("local.title");
        String signInButton = myResources.getValue("local.signInButton");
        String signUpButton = myResources.getValue("local.signUpButton");

        JsonObject data = Json.createObjectBuilder()
                .add("title", new String(title.getBytes(), "utf-8"))
                .add("signInButton", new String(signInButton.getBytes(), "UTF-8"))
                .add("signUpButton", new String(signUpButton.getBytes(), "UTF-8"))
                .build();

        System.out.println(data);
    }

    @Test
    public void testIteration() {
        myResources = new MyResources(new Locale("ru"));
        Set<String> keys = myResources.getBundle().keySet();
        JsonObjectBuilder object = Json.createObjectBuilder();

        for (String key : keys)
            object.add(key.replace("local.", ""), myResources.getValue(key));

        JsonObject data = object.build();
        System.out.println(data);
    }

    @Test
    public void testEncryptPassword() {
        String psw_1 = EncryptPassword.encryptPassword("qq");
        String psw_2 = EncryptPassword.encryptPassword("qq");
        System.out.println(psw_1 + " " + psw_2);
    }
}

class MyResources {
    private ResourceBundle bundle;

    public MyResources(Locale locale) {
        bundle = ResourceBundle.getBundle("locale", locale);
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
