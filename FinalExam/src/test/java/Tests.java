import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class Tests {
    @Test
    public void testResourseBundle() {
        Locale locale = Locale.ENGLISH;
        ResourceBundle myResources = ResourceBundle.getBundle("locale",
                locale);

        String string = myResources.getString("local.title");
        System.out.println("local.title: " + string);
    }
}
