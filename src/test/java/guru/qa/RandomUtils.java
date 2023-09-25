package guru.qa;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class RandomUtils {
    static Faker myFaker = new Faker();

    String userEmail = myFaker.internet().emailAddress();

    JSONObject  jsonUserEmail = new JSONObject()
                .put("email", userEmail);
}
