package guru.qa;

import com.github.javafaker.Faker;
import org.json.JSONObject;

import java.time.Instant;

public class RandomUtils {
    static Faker myFaker = new Faker();

    String userEmail = myFaker.internet().emailAddress(),
            userName = myFaker.name().firstName(),
            userJob = myFaker.job().position(),
            userPassword = myFaker.internet().password(),
            timeBeforeStartTest = Instant.now().toString();

    JSONObject jsonBodyCreate = new JSONObject()
            .put("name", userName)
            .put("job", userJob);
    JSONObject jsonBodyRegisterFail = new JSONObject()
            .put("email", userEmail)
            .put("password", userPassword);

    JSONObject jsonBodyRegister = new JSONObject()
            .put("email", "eve.holt@reqres.in")
            .put("password", userPassword);

    JSONObject jsonUserEmail = new JSONObject()
            .put("email", userEmail);
}
