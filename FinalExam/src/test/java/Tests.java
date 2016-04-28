import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tests {
    private MyResources myResources;

    @Test
    public void testResourseBundle() throws UnsupportedEncodingException {
        MyResources myResources = new MyResources(new Locale("en"));
//        String title = myResources.getValue("local.title");
        System.out.println("local.title: " + myResources.getValue("local.title"));

        myResources = new MyResources(new Locale("ru"));

        String title = myResources.getValue("local.title");
        System.out.println(new String(title.getBytes(), "UTF-8"));
//        System.out.println("local.title: " + myResources.getValue("local.title"));
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
}

class MyResources {
    private ResourceBundle bundle;

    public MyResources(Locale locale) {
        bundle = ResourceBundle.getBundle("locale", locale);
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
